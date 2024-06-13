package com.example.minhasdespesas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.ExpenseEntity
import com.example.minhasdespesas.data.BudgetEntity
import com.example.minhasdespesas.data.repository.DataBaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TITLE)

    private val _myBudget = MutableStateFlow("")
    val myBudget: StateFlow<String> = _myBudget.asStateFlow()

    init {
        viewModelScope.launch {
            dataBaseRepository.getBudgetFlow().collect { expenseValue ->
                _myBudget.value = expenseValue
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _expenses = _sortType
        .flatMapLatest { sortType ->

            when (sortType) {
                SortType.TITLE -> this.dataBaseRepository.getAllExpenseByName()
                SortType.VALUE -> this.dataBaseRepository.getAllExpenseByValue()
                SortType.CATEGORY -> this.dataBaseRepository.getAllExpenseByCategory()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _expensesState = MutableStateFlow(ExpenseState())
    val expensesState =
        combine(_expensesState, _sortType, _expenses) { expensesState, sortType, expenses ->
            expensesState.copy(
                expensesList = expenses,
                sortType = sortType,

                )

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseState())


    fun onEvent(event: ExpenseEvent) {

        when (event) {
            is ExpenseEvent.DeleteExpenses -> {

                viewModelScope.launch {

                    val expenseById = dataBaseRepository.getExpenseById(event.expenseEntity.id)

                    if (expenseById != null) {
                        dataBaseRepository.deleteExpenseById(expenseById.id)

                        // Calculate Budge value
                        val budgetFlow = dataBaseRepository.getBudgetFlow()
                        val budgetString = budgetFlow.firstOrNull()?.toString()
                        val budgetDouble = budgetString?.toDoubleOrNull() ?: 0.0
                        val expenseByIdValue = expenseById.expenseValue.toDoubleOrNull() ?: 0.0
                        val newBudget = budgetDouble + expenseByIdValue
                        val newBudgetObj = BudgetEntity(budget = newBudget.toString())

                        dataBaseRepository.updateBudget(newBudgetObj)
                    }
                }
            }

            ExpenseEvent.HideDialog -> {
                _expensesState.update { it.copy(isAddingExpense = false) }
                _expensesState.update { it.copy(isAddingMoney = false) }
            }

            ExpenseEvent.ExpenseShowDialog -> {
                _expensesState.update { it.copy(isAddingExpense = true) }

            }

            ExpenseEvent.MoneyShowDialog -> {
                _expensesState.update { it.copy(isAddingMoney = true) }
            }

            ExpenseEvent.SaveExpense -> {
                val title = expensesState.value.title
                val expenseValue = expensesState.value.expenseValue
                val category = expensesState.value.category

                if (title.isBlank() || expenseValue.isBlank() || category.isBlank()) {

                    return
                }

                val expenseEntity = ExpenseEntity(
                    title = title,
                    expenseValue = expenseValue,
                    category = category
                )

                viewModelScope.launch {
                    dataBaseRepository.upsertExpense(expenseEntity)

                    // Calculate budget value
                    val budgetFlow = dataBaseRepository.getBudgetFlow()
                    val budgetString = budgetFlow.firstOrNull().toString()
                    val expenseValueDouble = expenseEntity.expenseValue.toDoubleOrNull() ?: 0.0
                    val budgetDouble = budgetString.toDoubleOrNull() ?: 0.0
                    val newBudget = budgetDouble - expenseValueDouble
                    val newBudgetObj = BudgetEntity(budget = newBudget.toString())

                    dataBaseRepository.insertBudget(newBudgetObj)
                    dataBaseRepository.updateBudget(newBudgetObj)
                }

                _expensesState.update {
                    it.copy(
                        isAddingExpense = false,
                        title = "",
                        expenseValue = "",
                        category = "",

                        )
                }
            }

            ExpenseEvent.SaveMoney -> {

                viewModelScope.launch {

                    // Calculate Budget Value
                    val myMoney = expensesState.value.myMoney.toDoubleOrNull() ?: 0.0
                    val budgetFlow = dataBaseRepository.getBudgetFlow()
                    val budgetDouble = budgetFlow.firstOrNull()?.toDoubleOrNull() ?: 0.0
                    val newBudget = budgetDouble + myMoney
                    val newBudgetObj = BudgetEntity(id = 1, budget = newBudget.toString())

                    dataBaseRepository.insertBudget(newBudgetObj)

                    _expensesState.update {
                        it.copy(isAddingMoney = false, myMoney = "")
                    }
                }
            }

            is ExpenseEvent.SetCategory -> {
                _expensesState.update { it.copy(category = event.category) }
            }

            is ExpenseEvent.SetName -> {
                _expensesState.update { it.copy(title = event.name) }
            }

            is ExpenseEvent.SetValor -> {
                _expensesState.update { it.copy(expenseValue = event.value) }
            }

            is ExpenseEvent.SortExpenses -> {
                _sortType.value = event.sortType
            }

            is ExpenseEvent.SetMoney -> {
                _expensesState.update {
                    it.copy(myMoney = event.money)
                }
            }


            else -> {}
        }
    }

}
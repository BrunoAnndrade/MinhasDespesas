package com.example.minhasdespesas.Presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.Data.ExpenseEntity
import com.example.minhasdespesas.Data.ExpenseDao
import com.example.minhasdespesas.Data.BudgetEntity
import com.example.minhasdespesas.ExpenseEvent
import com.example.minhasdespesas.ExpenseState
import com.example.minhasdespesas.ExpensesApplication
import com.example.minhasdespesas.SortType
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

class ExpenseViewModel(private val expenseDao: ExpenseDao) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TITLE)

    private val _myBudget = MutableStateFlow("")
    val myBudget: StateFlow<String> = _myBudget.asStateFlow()

    init {
        viewModelScope.launch {
            expenseDao.getBudgetFlow().collect { expenseValue ->
                _myBudget.value = expenseValue

            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private val _expenses = _sortType
        .flatMapLatest { sortType ->

            when (sortType) {
                SortType.TITLE -> this.expenseDao.getAllExpenseByName()
                SortType.VALUE -> this.expenseDao.getAllExpenseByValue()
                SortType.CATEGORY -> this.expenseDao.getAllExpenseByCategory()
            }

            // Use stateIn to create state Flow with data with the data resulting from the mapping
            // operation (flatMapLatest).
            // ViewModelScope because when flow is canceled appropriately when the ViewModel is deactivated.
            // SharingStarted specifies that stream collection should begin when an observer is subscribed
            // to the stream and end when all observers are unsubscribed.
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
        val myMoney = expensesState.value.myMoney.toDoubleOrNull() ?: 0.0

        when (event) {
            is ExpenseEvent.DeleteExpenses -> {

                viewModelScope.launch {

                    val expenseById = expenseDao.getExpenseById(event.expenseEntity.id)

                    if (expenseById != null){
                        expenseDao.deleteExpense(expenseById)

                        // Update the orcamento
                        val budgetFlow = expenseDao.getBudgetFlow()
                        val budgetString = budgetFlow.firstOrNull()?.toString()
                        val budgetDouble = budgetString?.toDoubleOrNull() ?: 0.0
                        val expenseValue = expenseById.expenseValue.toDoubleOrNull() ?: 0.0

                        // Calculate the new budget
                        val newBudget = budgetDouble + expenseValue

                        // Update the budget in the database
                        val newBudgetObj = BudgetEntity(budget = newBudget.toString())
                        expenseDao.updateBudget(newBudgetObj)

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
                    expenseDao.upsertExpense(expenseEntity)

                    val budgetFlow = expenseDao.getBudgetFlow()
                    val budgetString = budgetFlow.firstOrNull()?.toString()

                    // Convert String to double
                    val expenseValueDouble = expenseEntity.expenseValue.toDoubleOrNull() ?: 0.0
                    val budgetDouble = budgetString?.toDoubleOrNull() ?: 0.0

                    // Calculate the new budget
                    val newBudget = budgetDouble - expenseValueDouble

                    // Create a new Orçamento object with the updated value
                    val newBudgetObj = BudgetEntity(budget = newBudget.toString())

                    // Update the budget in the database
                    expenseDao.updateBudget(newBudgetObj)
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

                    val budgetFlow = expenseDao.getBudgetFlow()
                    val budgetDouble = budgetFlow.firstOrNull()?.toDoubleOrNull() ?: 0.0

                    //calculate new budget
                    val newBudget = budgetDouble + myMoney

                    // create a new Orcamento objet with the updated value
                    val newBudgetObj = BudgetEntity(id = 1, budget = newBudget.toString())

                    //update budget into database
                    expenseDao.insertBudget(newBudgetObj)

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
    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as ExpensesApplication).getAppDataBase()
            val dao = dataBaseInstance.expenseDao()
            val factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ExpenseViewModel(dao) as T
                }
            }
            return factory
        }
    }
}
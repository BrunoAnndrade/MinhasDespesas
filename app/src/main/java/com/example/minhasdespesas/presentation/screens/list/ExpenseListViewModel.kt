package com.example.minhasdespesas.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.data.repository.BudgetRepository
import com.example.minhasdespesas.data.repository.CategoryRepository
import com.example.minhasdespesas.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository,
) : ViewModel() {

    private val _expensesList = MutableStateFlow<List<ExpenseEntity>>(emptyList())
    val expensesList: StateFlow<List<ExpenseEntity>> = _expensesList.asStateFlow()

    init {
        viewModelScope.launch {
            getAllExpenses()
        }
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            expenseRepository.getAllExpenses().collect { expenses ->
                _expensesList.value = expenses
            }
        }
    }

    fun filterExpenseByCategoryName(categoryName: String) {
        viewModelScope.launch {
            if (categoryName == "All") {
                expenseRepository.getAllExpenses().collect { expenses ->
                    _expensesList.value = expenses
                }
            } else {
                expenseRepository.getExpenseByCategoryName(categoryName).collect { expenses ->
                    _expensesList.value = expenses.filter { it.category == categoryName }
                }
            }
        }
    }

    fun insertExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            expenseRepository.upsertExpense(expense)
        }
    }

    fun deleteExpense(expenseId: Int, expense: ExpenseEntity) {
        viewModelScope.launch {
            expenseRepository.deleteExpenseById(expenseId)

            val budgetFlow = budgetRepository.getBudgetFlow()
            val budgetDouble = budgetFlow.firstOrNull()?.toDouble()
            val expenseByIdValue = expense.expenseValue.toDoubleOrNull() ?: 0.0
            val newBudget = budgetDouble?.plus(expenseByIdValue)
            val newBudgetObj = BudgetEntity(budget = newBudget.toString())

            budgetRepository.updateBudget(newBudgetObj)
        }
    }
}









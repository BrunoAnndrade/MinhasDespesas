package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.data.repository.BudgetRepository
import com.example.minhasdespesas.data.repository.CategoryRepository
import com.example.minhasdespesas.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository
):ViewModel() {

    private val _expensesUi = MutableStateFlow<ExpenseEntity?>(null)
    val expensesUi: StateFlow<ExpenseEntity?> = _expensesUi

    fun fetchExpenseDetail(moviesId: String?){
        viewModelScope.launch(Dispatchers.IO) {
            val data = moviesId?.let { expenseRepository.getExpenseById(it) }
            _expensesUi.value = data
        }
    }

    val listColors = listOf(
        Color(0xFFE57373),
        Color(0xFFF06292),
        Color(0xFFBA68C8),
        Color(0xFF9575CD),
        Color(0xFF7986CB),
        Color(0xFF64B5F6),
        Color(0xFF4FC3F7),
        Color(0xFF4DD0E1),
        Color(0xFF4DB6AC),
        Color(0xFF81C784),
        Color(0xFFAED581),
        Color(0xFFFF8A65),
        Color(0xFFFFA726),
    )

    fun editAndSaveExpense(
        expenseName: String,
        expenseValue: String,
        category: String,
        id:String
    ) {
        viewModelScope.launch {
            val categories = CategoryEntity(
                name = category,
            )
            val expenses = ExpenseEntity(
                title = expenseName,
                expenseValue = expenseValue,
                category = category,
                id = id.toInt()

            )
            categoryRepository.insertCategory(categories)
            expenseRepository.upsertExpense(expenses)

            val budgetFlow = budgetRepository.getBudgetFlow()
            val budgetString = budgetFlow.firstOrNull().toString()
            val expenseValueDouble = expenses.expenseValue.toDoubleOrNull() ?: 0.0
            val budgetDouble = budgetString.toDoubleOrNull() ?: 0.0
            val newBudget = budgetDouble - expenseValueDouble
            val newBudgetObj = BudgetEntity(budget = newBudget.toString())

            budgetRepository.insertBudget(newBudgetObj)
            budgetRepository.updateBudget(newBudgetObj)
        }
    }
}
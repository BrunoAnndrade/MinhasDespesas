package com.example.minhasdespesas.presentation.screens.expenseDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.data.repository.BudgetRepository
import com.example.minhasdespesas.data.repository.CategoryRepository
import com.example.minhasdespesas.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseBottomSheetViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
):ViewModel() {

    fun saveNewExpense(
        expenseName: String,
        expenseValue: Double,
        category: String,
        selectedColor: String,
        date: Long?
    ){
        viewModelScope.launch {
            val categories = CategoryEntity(
                name = category,
            )
            val expenses = ExpenseEntity(
                title = expenseName,
                expenseValue = expenseValue,
                category = category,
                color = selectedColor,
                date = date

            )
            categoryRepository.insertCategory(categories)
            expenseRepository.upsertExpense(expenses)
        }
    }
}
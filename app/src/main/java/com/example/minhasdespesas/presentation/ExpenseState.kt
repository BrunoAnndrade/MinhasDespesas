package com.example.minhasdespesas.presentation

import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity


data class ExpenseState(
    val expensesList: List<ExpenseEntity> = emptyList(),
    val category: String = "",
    val title: String = "",
    val expenseValue: String = "",
    val myMoney:String = "",
    val isAddingExpense: Boolean = false,
    val isAddingMoney:Boolean = false,
    val sortType: SortType = SortType.TITLE,
)
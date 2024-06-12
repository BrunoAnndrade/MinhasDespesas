package com.example.minhasdespesas.presentation

import com.example.minhasdespesas.data.ExpenseEntity


data class ExpenseState(
    val expensesList: List<ExpenseEntity> = emptyList(),
    val title: String = "",
    val expenseValue: String = "",
    val category: String = "",
    val myMoney:String = "",
    val isAddingExpense: Boolean = false,
    val isAddingMoney:Boolean = false,
    val sortType: SortType = SortType.TITLE
)
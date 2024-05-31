package com.example.minhasdespesas

import com.example.minhasdespesas.Data.ExpenseEntity


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
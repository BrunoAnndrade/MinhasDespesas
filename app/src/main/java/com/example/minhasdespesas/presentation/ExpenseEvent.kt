package com.example.minhasdespesas.presentation

import com.example.minhasdespesas.data.ExpenseEntity

sealed interface ExpenseEvent {

    data object SaveExpense : ExpenseEvent
    data object SaveMoney: ExpenseEvent
    data class SetName(val name: String) : ExpenseEvent
    data class SetValor(val value: String) : ExpenseEvent
    data class SetCategory(val category: String) : ExpenseEvent
    data class SetMoney(val money: String): ExpenseEvent
    data object ExpenseShowDialog : ExpenseEvent
    data object MoneyShowDialog : ExpenseEvent
    data object HideDialog : ExpenseEvent
    data class SortExpenses(val sortType: SortType) : ExpenseEvent
    data class DeleteExpenses(val expenseEntity: ExpenseEntity) : ExpenseEvent
}
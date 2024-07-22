package com.example.minhasdespesas.presentation

sealed class NavScreens(val route: String) {
    data object ExpenseList : NavScreens("expenseList")
    data object ExpenseDetail : NavScreens("expenseDetail/{expenseId}") {
        fun createRoute(expenseId: String) = "expenseDetail/$expenseId"
    }
}
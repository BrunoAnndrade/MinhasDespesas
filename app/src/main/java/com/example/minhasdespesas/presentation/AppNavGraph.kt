package com.example.minhasdespesas.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.minhasdespesas.presentation.screens.detail.ExpenseDetail
import com.example.minhasdespesas.presentation.screens.list.ExpensesScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = "expenseList"
    ) {
        composable(
            route = "expenseDetail" + "/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseId = requireNotNull(backStackEntry.arguments?.getString("expenseId"))


            ExpenseDetail(
                expenseId = expenseId
            )
        }

        composable(route = "expenseList") {
            ExpensesScreen(navController)
        }
    }
}
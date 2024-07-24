package com.example.minhasdespesas.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.presentation.screens.ExpenseBottomSheet
import com.example.minhasdespesas.presentation.screens.ExpensesScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    val showSheet by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = "expenseList"
    ) {
        composable(
            route = "expenseDetail" + "/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseId = requireNotNull( backStackEntry.arguments?.getString("expenseId"))
            ExpenseBottomSheet(expenseId = expenseId, onDismiss = { },navController)
        }

        composable(route = "expenseList") {
            ExpensesScreen(navController)
        }
    }
}
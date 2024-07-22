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
fun AppNavGraph(
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
    budgetViewModel: BudgetViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),

) {
    val expenses by expenseViewModel.expensesList.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavScreens.ExpenseList.route
    ) {

        composable(
            route = NavScreens.ExpenseDetail.route,
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getString("expenseId")?.toIntOrNull()
            val expenseItem = expenses.find { it.id == expenseId }

            expenseItem?.let {
                ExpenseBottomSheet(
                    onDismiss = {showSheet = false }
                    ,categoryViewModel,
                    expenseViewModel
                )
            }
        }


        composable(route = NavScreens.ExpenseList.route) {
            ExpensesScreen(expenseViewModel,budgetViewModel,navController)
        }



    }

}
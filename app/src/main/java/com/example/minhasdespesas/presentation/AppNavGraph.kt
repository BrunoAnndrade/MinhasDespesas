package com.example.minhasdespesas.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.presentation.screens.ExpenseBottomSheet
import com.example.minhasdespesas.presentation.screens.ExpensesScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    var showSheet by remember { mutableStateOf(true) }
    val expenseViewModel: ExpenseViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = "expenseList"
    ) {
        composable(
            route = "expenseDetail" + "/{expenseId}",
            arguments = listOf(navArgument("expenseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val expenseId = requireNotNull(backStackEntry.arguments?.getString("expenseId"))
            val expenseItem =
                expenseViewModel.expensesList.value.find { it.id.toString() == expenseId }



            ExpenseBottomSheet(
                navController,
                expenseItem,
                onDismiss = { navController.navigate("expenseList") }
            )


        }

        composable(route = "expenseList") {
            ExpensesScreen(navController)
        }
    }
}
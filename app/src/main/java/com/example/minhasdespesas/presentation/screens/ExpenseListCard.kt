package com.example.minhasdespesas.presentation.screens

import android.graphics.fonts.FontStyle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.presentation.BudgetViewModel
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseDetailViewModel
import com.example.minhasdespesas.presentation.ExpenseViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListCard(
    navController: NavHostController
) {
    val expenseViewModel: ExpenseViewModel = hiltViewModel()
    val expenseList by expenseViewModel.expensesList.collectAsState()

    Box {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(expenseList) { expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val expenseId = expense.id.toString()
                            navController.navigate("expenseDetail/${expenseId}")
                        }
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = expense.title,
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                            )
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Categoria: ${expense.category}")
                        Text(text = "Valor: R$ ${expense.expenseValue},00")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseListCardPreview() {

    val navController = NavHostController(LocalContext.current)
    ExpenseListCard(navController)

}
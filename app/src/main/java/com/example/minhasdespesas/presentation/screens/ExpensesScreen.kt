package com.example.minhasdespesas.presentation.screens


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.presentation.ExpenseViewModel
import com.example.minhasdespesas.R
import com.example.minhasdespesas.presentation.BudgetViewModel
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navController: NavHostController,
) {
    val expenseViewModel:ExpenseViewModel = hiltViewModel()
    val expenseDetailViewModel:ExpenseDetailViewModel = hiltViewModel()
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val budgetViewModel: BudgetViewModel = hiltViewModel()

    val budget by budgetViewModel.myBudget.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Row {
                Text(
                    text = "Orçamento ",
                    style = TextStyle.Default.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "R$ $budget ",
                    style = TextStyle.Default.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterVertically)
                )

                Button(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent

                    ),

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_money4),
                        contentDescription = "money",
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Top),
                    )
                }
            }

            ExpenseListCard(navController)

        }

    }


}











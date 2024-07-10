package com.example.minhasdespesas.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minhasdespesas.presentation.ExpenseViewModel


@Composable
fun ExpenseListCard (
    viewModel: ExpenseViewModel = viewModel()
){

    val expenseList by viewModel.expensesList.collectAsState()

    Box(

    ){

        LazyColumn(

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            items(expenseList){ expense ->
                Row {
                    Column {
                        Row {
                            Text(text = expense.title)
                            Text(text = expense.category)
                        }

                        Text(text = expense.expenseValue)
                    }
                }

            }


        }
    }

}
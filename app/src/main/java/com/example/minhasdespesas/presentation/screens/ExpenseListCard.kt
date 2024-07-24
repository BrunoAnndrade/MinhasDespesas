package com.example.minhasdespesas.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.presentation.ExpenseViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListCard (
    viewModel: ExpenseViewModel = viewModel(),
    navController: NavHostController
){
    val expenseList by viewModel.expensesList.collectAsState()

    Box{
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(expenseList){ expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val expenseId = expense.id.toString()
                            Log.d("BRUNO", "Navigating to ExpenseDetail with id: $expenseId")
                            navController.navigate("expenseDetail/${expenseId}")
                        }
                        .padding(16.dp)
                ) {
                    Column(

                    ) {
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
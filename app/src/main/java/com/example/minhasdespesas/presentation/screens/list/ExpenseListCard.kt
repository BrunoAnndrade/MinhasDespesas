package com.example.minhasdespesas.presentation.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.ui.theme.Purple40
import android.graphics.Color.parseColor
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.ui.theme.Purple20
import com.example.minhasdespesas.ui.theme.PurpleBorder


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseListCard(
    navController: NavHostController,
    expenseListViewModel: ExpenseListViewModel = hiltViewModel(),
) {
    val expense by expenseListViewModel.expensesList.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }
    val selectedExpense = remember { mutableStateOf<ExpenseEntity?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 10.dp),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(expense) { expenseItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, PurpleBorder, shape = RoundedCornerShape(10.dp))
                        .combinedClickable(
                            onClick = {
                                val expenseId = expenseItem.id.toString()
                                navController.navigate("expenseDetail/${expenseId}")
                            },
                            onLongClick = {
                                selectedExpense.value = expenseItem
                                showDeleteDialog.value = true
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (expenseItem.color?.isEmpty() == true) Color.White
                                else Color(parseColor(expenseItem.color)),
                                shape = RoundedCornerShape(
                                    topStart = 20.dp,
                                    bottomStart = 20.dp,
                                    topEnd = 0.dp,
                                    bottomEnd = 0.dp
                                ),
                            )
                            .width(20.dp)
                            .height(63.dp),
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp),
                        horizontalAlignment = Alignment.Start,

                        ) {
                        Text(
                            text = expenseItem.title,
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                            ),
                        )
                        expenseItem.category?.let {
                            Text(
                                text = it,
                                style = TextStyle.Default.copy(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                ),
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = expenseListViewModel.formatToCurrency(expenseItem.expenseValue.toString()),
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Purple40,
                                fontSize = 20.sp,
                            ),
                        )
                        Text(
                            text = expenseListViewModel.convertMillisToFormattedDate(expenseItem.date),
                            style = TextStyle.Default.copy(
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Light
                            ),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
        }
        if (showDeleteDialog.value) {
            selectedExpense.value?.let { expense ->
                DialogDeleteExpense(
                    expense = expense,
                    expenseId = expense.id,
                    onDismiss = {
                        showDeleteDialog.value = false

                    }
                )
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
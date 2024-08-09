package com.example.minhasdespesas.presentation.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.ui.theme.Purple40
import android.graphics.Color.parseColor
import androidx.compose.foundation.shape.CircleShape


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpenseListCard(
    navController: NavHostController,
    expenseViewModel: ExpenseViewModel = hiltViewModel()
) {
    val expense by expenseViewModel.expensesList.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(expense) { expenseItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .combinedClickable(
                            onClick = {
                                val expenseId = expenseItem.id.toString()
                                navController.navigate("expenseDetail/${expenseId}")
                            },
                            onLongClick = {
                                expenseViewModel.deleteExpense(
                                    expenseItem.id,
                                    expenseItem
                                )
                            }
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                if (expenseItem.color.isEmpty()) Color.White
                                else Color(parseColor(expenseItem.color)),
                                shape = CircleShape
                            )
                            .width(20.dp)
                            .height(20.dp)
                    ) {

                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 5.dp),
                        horizontalAlignment = Alignment.Start,

                        ) {
                        Text(
                            text = expenseItem.title,
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 20.sp,
                            ),
                        )
                        Text(
                            text = expenseItem.category,
                            style = TextStyle.Default.copy(
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                        )
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = "${expenseItem.expenseValue},00",
                            style = TextStyle.Default.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Purple40,
                                fontSize = 20.sp,
                            ),
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray),
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
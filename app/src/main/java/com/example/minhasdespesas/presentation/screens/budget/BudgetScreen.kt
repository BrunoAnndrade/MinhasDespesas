package com.example.minhasdespesas.presentation.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.ui.theme.Purple40


@Composable
fun BudgetScreen(
    budgetViewModel: BudgetViewModel = hiltViewModel()
) {
    val budget by budgetViewModel.myBudget.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(2.dp, Purple40, RoundedCornerShape(16.dp))
            .padding(15.dp)
    ) {

        if (showDialog) {
            MoneyDialog(onDismiss = { showDialog = false })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Saldo:",
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                ),
            )
            Text(
                text = budgetViewModel.formatToCurrency(budget),
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .padding(start = 5.dp, end = 10.dp)
            )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add money",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .background(Purple40, RoundedCornerShape(20.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .align(Alignment.CenterVertically)
                    .clickable { showDialog = true }
                ,
            )

        }
    }
}
package com.example.minhasdespesas.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.R
import com.example.minhasdespesas.presentation.BudgetViewModel
import com.example.minhasdespesas.ui.theme.Purple40
import com.example.minhasdespesas.ui.theme.PurpleLight


@Composable
fun BudgetScreen(){

    val budgetViewModel: BudgetViewModel = hiltViewModel()
    val budget by budgetViewModel.myBudget.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(PurpleLight)
            .fillMaxWidth()
    ){

        if (showDialog){
            MoneyDialog(onDismiss = {showDialog = false})
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

        ) {
            Text(
                text = "ORÇAMENTO",
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = "R$ $budget ",
                style = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp)

            )

            Button(
                onClick = {showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),

                ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_money4),
                    contentDescription = "money",
                    modifier = Modifier
                        .size(50.dp)

                )
            }
        }
    }
}
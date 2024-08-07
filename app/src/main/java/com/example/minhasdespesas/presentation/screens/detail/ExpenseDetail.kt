package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.ui.theme.Purple20
import com.example.minhasdespesas.ui.theme.Purple40



@Composable
fun ExpenseDetail(
    expenseDetailViewModel: ExpenseDetailViewModel = hiltViewModel(),
    expenseId: String? = null,
) {
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    val expenses = expenseDetailViewModel.expensesUi.collectAsState()
    val title = expenses.value?.title ?: ""
    val value = expenses.value?.expenseValue ?: ""
    val category = expenses.value?.category ?: ""

    expenseDetailViewModel.fetchExpenseDetail(expenseId)

    expenseName = title
    expenseValue = value
    newCategoryName = category

    Column(
        modifier = Modifier
            .imePadding()
            .padding(25.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Detalhes",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(Purple40, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp)

            ,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        TextField(
            value = expenseName,
            onValueChange = { expenseName = it },
            label = { Text("Nome da despesa") },
            colors = TextFieldDefaults.colors(
                errorPlaceholderColor = Color.Red,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
            ,

        )
        TextField(
            value = expenseValue,
            onValueChange = { expenseValue = it },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            label = { Text("Valor") },
            colors = TextFieldDefaults.colors(
                errorPlaceholderColor = Color.Red,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth(),

        )
        TextField(
            value = newCategoryName,
            onValueChange = { newCategoryName = it },
            label = { Text("Categoria") },
            colors = TextFieldDefaults.colors(
                errorPlaceholderColor = Color.Red,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                expenseDetailViewModel.editAndSaveExpense(
                    expenseName,
                    expenseValue,
                    newCategoryName,
                    expenseId.toString()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
            ,
            colors = ButtonColors(
                containerColor = Purple20,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
        ) {
            Text(
                text = "Salvar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
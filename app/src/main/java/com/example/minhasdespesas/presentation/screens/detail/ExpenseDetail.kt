package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.presentation.screens.category.CategoryViewModel
import com.example.minhasdespesas.presentation.screens.category.DialogDeleteCategory
import com.example.minhasdespesas.ui.theme.Purple20
import com.example.minhasdespesas.ui.theme.Purple40


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDetail(
    expenseDetailViewModel: ExpenseDetailViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    expenseId: String? = null,
) {
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var categoryName by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val expenses by expenseDetailViewModel.expensesUi.collectAsState()
    val categories by categoryViewModel.categories.collectAsState()
    val title = expenses?.title ?: ""
    val value = expenses?.expenseValue ?: ""
    val category = expenses?.category ?: ""

    expenseDetailViewModel.fetchExpenseDetail(expenseId)

    LaunchedEffect(expenses) {
        expenseName = title
        expenseValue = value
        newCategoryName = category
    }

    Column(
        modifier = Modifier
            .imePadding()
            .padding(25.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Detalhes",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .background(Purple40, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        OutlinedTextField(
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
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = newCategoryName,
                onValueChange = { newCategoryName = it },
                label = { Text("Categoria") },
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir/Colapsar Lista"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (expanded) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                ,
            ) {
                items(categories) { category ->
                    Text(
                        text = category.name,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Purple20, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                newCategoryName = category.name
                            }
                            .padding(8.dp),
                        color = Color.White,
                    )
                }
            }
        }

        Button(
            onClick = {
                expenseDetailViewModel.editAndSaveExpense(
                    expenseName, expenseValue, newCategoryName, expenseId.toString()
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonColors(
                containerColor = Purple20,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
        ) {
            Text(
                text = "Salvar", fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
        }
    }
}

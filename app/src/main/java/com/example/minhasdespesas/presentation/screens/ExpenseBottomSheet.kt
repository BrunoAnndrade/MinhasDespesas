package com.example.minhasdespesas.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBottomSheet(
    onDismiss: () -> Unit,
    categoryViewModel: CategoryViewModel = viewModel(),
    expenseViewModel: ExpenseViewModel = viewModel(),

    ) {
    val categories by categoryViewModel.categories.collectAsState()
    val expenses by expenseViewModel.expensesList.collectAsState()

    val modalBottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<CategoryEntity?>(null) }

    val isDropdownExpanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {

            TextField(
                value = expenseName,
                onValueChange = { expenseName = it },
                label = { Text("Expense Name") },
                modifier = Modifier.fillMaxWidth(),
            )

            TextField(
                value = expenseValue,
                onValueChange = { expenseValue = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Expense Value") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = newCategoryName,
                onValueChange = { newCategoryName = it },
                label = { Text("Category Value") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = false,
                onDismissRequest = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {

                            coroutineScope.launch {
                                selectedCategory = category
                                modalBottomSheetState.hide()
                            }
                        },
                        text = { Text(category.name) }
                    )
                }
            }

            Button(
                onClick = {
                    expenseViewModel.saveExpense(expenseName,expenseValue,newCategoryName)

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "salvar")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun BottomSheetExamplePreview() {
    ExpenseBottomSheet( onDismiss = {})
}
package com.example.minhasdespesas.presentation.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseEvent
import com.example.minhasdespesas.presentation.ExpenseState
import kotlinx.coroutines.launch

@Composable
fun ExpenseDialog(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
    modifier: Modifier = Modifier,
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val categories by categoryViewModel.categories.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(ExpenseEvent.HideDialog) },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(ExpenseEvent.SaveExpense)


                }
            ) {
                Text(text = "Salvar Despesa")

            }
        },
        title = { Text(text = "ADICIONE DESPESA") },
        text = {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                TextField(
                    value = state.title,
                    onValueChange = { onEvent(ExpenseEvent.SetName(it)) },
                    placeholder = { Text(text = "título") }
                )
                TextField(
                    value = state.expenseValue,
                    onValueChange = { onEvent(ExpenseEvent.SetValor(it)) },
                    placeholder = { Text(text = "valor") }
                )

                TextField(
                    value = state.category,
                    onValueChange = {
                        selectedCategory = it
                        onEvent(ExpenseEvent.SetCategory(it))
                    },
                    placeholder = { Text(text = "categoria") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    readOnly = false
                )
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Selecionar Categoria")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategory = category.name
                                onEvent(ExpenseEvent.SetCategory(category.name))
                                expanded = false
                            }
                        )
                    }

                }
            }
        }
    )
}
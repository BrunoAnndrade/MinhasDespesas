package com.example.minhasdespesas.Presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minhasdespesas.ExpenseEvent
import com.example.minhasdespesas.ExpenseState

@Composable
fun ExpenseDialog(
    state: ExpenseState,
    onEvent: (ExpenseEvent) -> Unit,
    modifier: Modifier = Modifier
) {
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
                    onValueChange = { onEvent(ExpenseEvent.SetCategory(it)) },
                    placeholder = { Text(text = "categoria") }
                )

            }
        },


        )
}
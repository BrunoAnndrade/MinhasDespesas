package com.example.minhasdespesas.presentation.screens.list

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.ui.theme.Purple40

@Composable
fun DialogDeleteExpense(
    expense: ExpenseEntity,
    expenseId: Int,
    onDismiss: () -> Unit,
    expenseViewModel: ExpenseListViewModel = hiltViewModel(),
){

    AlertDialog(
        title = {
            Text(
                text = "Deseja deletar a despesa ${expense.title}?",
                color = Purple40,
                textAlign =  TextAlign.Start
            )
        },
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {
            Button(
                onClick = {
                    expenseViewModel.deleteExpense(expenseId, expense)
                    onDismiss()
                }
            ) {
                Text(text = "Sim")
            }

        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(text = "Não")
            }
        },
    )
}
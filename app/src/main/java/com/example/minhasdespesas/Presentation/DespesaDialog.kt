package com.example.despesasdescomplicadas.Presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.despesasdescomplicadas.DespesaEvent
import com.example.despesasdescomplicadas.DespesaState

@Composable
fun DespesaDialog(
    state: DespesaState,
    onEvent: (DespesaEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(DespesaEvent.HideDialog) },
        confirmButton = {
            Button(
                onClick = {
                    onEvent(DespesaEvent.SaveDespesa)

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
                    onValueChange = { onEvent(DespesaEvent.SetName(it)) },
                    placeholder = { Text(text = "título") }
                )
                TextField(
                    value = state.valor,
                    onValueChange = { onEvent(DespesaEvent.SetValor(it)) },
                    placeholder = { Text(text = "valor") }
                )
                TextField(
                    value = state.category,
                    onValueChange = { onEvent(DespesaEvent.SetCategory(it)) },
                    placeholder = { Text(text = "categoria") }
                )

            }
        },


        )
}
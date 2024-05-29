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
fun MoneyDialog(
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
                    onEvent(DespesaEvent.SaveMoney)

                }
            ) {
                Text(text = "Salvar dinheiro")

            }
        },
        title = { Text(text = "ADICIONE UM VALOR") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.dinheiro,
                    onValueChange = {onEvent(DespesaEvent.SetMoney(it))
                    },
                    placeholder = { Text(text = "Dinheiro") }
                )

            }
        },


        )
}
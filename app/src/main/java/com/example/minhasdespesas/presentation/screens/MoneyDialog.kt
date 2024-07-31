package com.example.minhasdespesas.presentation.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.presentation.BudgetViewModel
import kotlinx.coroutines.launch

@Composable
fun MoneyDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {

    val budgetViewModel: BudgetViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()
    var money by rememberSaveable { mutableStateOf("") }
    var showDialog = remember { mutableStateOf(false) }


    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        budgetViewModel.saveMoney(money.toDouble())
                    }
                }
            ) {
                Text(text = "Salvar")

            }
        },
        title = { Text(text = "Adicione um valor") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                TextField(
                    value = money,
                    onValueChange = {money = it},
                    label = { Text("Valor") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )


            }
        },


        )
}

@Preview(showBackground = true)
@Composable
fun MoneyDialogPreview() {
    MoneyDialog()
}
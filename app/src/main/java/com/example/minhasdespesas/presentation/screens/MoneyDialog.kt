package com.example.minhasdespesas.presentation.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minhasdespesas.presentation.CategoryViewModel

@Composable
fun MoneyDialog(

    modifier: Modifier = Modifier,

    ) {


    AlertDialog(
        modifier = modifier,
        onDismissRequest = {  },
        confirmButton = {
            Button(
                onClick = {


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



            }
        },


        )
}
package com.example.minhasdespesas.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.presentation.BottomSheetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBottomSheet(
    navController: NavHostController,
    bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = {onDismiss()},
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .imePadding()

        ) {
            TextField(
                value =  expenseName ,
                onValueChange = { expenseName = it },
                label = { Text("Expense Name") },
            )
            TextField(
                value = expenseValue ,
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
            Button(
                onClick = {
                    bottomSheetViewModel.saveNewExpense(
                        expenseName,
                        expenseValue,
                        newCategoryName,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Salvar")
            }
        }
    }
}




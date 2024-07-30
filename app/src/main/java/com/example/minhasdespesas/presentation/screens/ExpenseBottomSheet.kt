package com.example.minhasdespesas.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseDetailViewModel
import com.example.minhasdespesas.presentation.ExpenseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBottomSheet(
    navController: NavHostController,
    expenses: ExpenseEntity? = null,
    onDismiss: () -> Unit = {}
) {
    val expenseViewModel: ExpenseViewModel = hiltViewModel()
    val modalBottomSheetState = rememberModalBottomSheetState()
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }

    val title = expenses?.title ?: ""
    val value = expenses?.expenseValue ?: ""
    val category = expenses?.category ?: ""

    ModalBottomSheet(
        onDismissRequest = {onDismiss()},
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column {
            TextField(
                value = title.ifEmpty { expenseName },
                onValueChange = { expenseName = it },
                label = { Text("Expense Name") },
            )
            TextField(
                value = value.ifEmpty { expenseValue },
                onValueChange = { expenseValue = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Expense Value") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = category.ifEmpty { newCategoryName },
                onValueChange = { newCategoryName = it },
                label = { Text("Category Value") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    expenseViewModel.saveExpense(expenseName, expenseValue, newCategoryName)
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




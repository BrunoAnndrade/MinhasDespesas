package com.example.minhasdespesas.presentation.screens.expenseDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import android.graphics.Color.parseColor
import com.example.minhasdespesas.presentation.screens.expenseDetail.components.DatePickerContent
import com.example.minhasdespesas.presentation.screens.expenseDetail.components.DropMenuCategories
import com.example.minhasdespesas.presentation.screens.expenseDetail.components.DropMenuColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseBottomSheet(
    bottomSheetViewModel: ExpenseBottomSheetViewModel = hiltViewModel(),
    expenseDetailViewModel: ExpenseDetailViewModel = hiltViewModel(),
    onDismiss: () -> Unit = {}
) {
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var expandedCategories by remember { mutableStateOf(false) }
    var expandedColors by remember { mutableStateOf(false) }
    var selectedColor by rememberSaveable { mutableStateOf("#E57373") }
    var date by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = rememberModalBottomSheetState(),
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .imePadding()
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = expenseName,
                onValueChange = { expenseName = it },
                label = { Text("Despesa") },
                colors = TextFieldDefaults.colors(
                    errorPlaceholderColor = Color.Red,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = expenseValue,
                onValueChange = { expenseValue = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Valor") },
                colors = TextFieldDefaults.colors(
                    errorPlaceholderColor = Color.Red,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.LightGray,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Categoria") },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        errorPlaceholderColor = Color.Red,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray
                    ),
                )
                IconButton(onClick = { expandedCategories = !expandedCategories }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir/Colapsar Lista"
                    )
                }
            }
            if (expandedCategories) {
                DropMenuCategories(onCategorySelected = {
                    newCategoryName = it
                    expandedCategories = false
                })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                        .background(Color(parseColor(selectedColor)), shape = CircleShape)
                        .padding(8.dp)
                )
                IconButton(onClick = { expandedColors = !expandedColors }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir/Colapsar Lista"
                    )
                }
            }
            if (expandedColors) {
                DropMenuColors(onColorSelected = { colorHex ->
                    selectedColor = colorHex
                })
            }

            DatePickerContent(date = date, onDateChange = { date = it })
            
            Text(
                text = "Obs:preencha todos os campos para salvar.",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    bottomSheetViewModel.saveNewExpense(
                        expenseName,
                        expenseValue,
                        newCategoryName,
                        selectedColor,
                        expenseDetailViewModel.convertDateToTimestamp(date)
                    )
                    onDismiss()
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




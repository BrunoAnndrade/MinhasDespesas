package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.presentation.components.MySnackBar
import com.example.minhasdespesas.presentation.screens.detail.dropMenu.DropMenuCategories
import com.example.minhasdespesas.presentation.screens.detail.dropMenu.DropMenuColors
import com.example.minhasdespesas.ui.theme.Purple20
import com.example.minhasdespesas.ui.theme.Purple40
import kotlinx.coroutines.launch
import kotlinx.datetime.format.FormatStringsInDatetimeFormats


@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun ExpenseDetail(
    expenseDetailViewModel: ExpenseDetailViewModel = hiltViewModel(),
    expenseId: String? = null,
) {
    var expenseName by rememberSaveable { mutableStateOf("") }
    var expenseValue by rememberSaveable { mutableStateOf("") }
    var newCategoryName by rememberSaveable { mutableStateOf("") }
    var selectedColor by rememberSaveable { mutableStateOf("#E57373") }
    var expandedCategories by remember { mutableStateOf(false) }
    var expandedColors by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf("") }
    val expenses by expenseDetailViewModel.expensesUi.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(expenses) {
        expenseDetailViewModel.fetchExpenseDetail(expenseId)
        expenseName = expenses?.title ?: ""
        expenseValue = expenses?.expenseValue ?: ""
        newCategoryName = expenses?.category ?: ""
        selectedColor = if (!expenses?.color.isNullOrEmpty()) expenses?.color!! else "#E57373"
        date = expenseDetailViewModel.convertMillisToFormattedDate(expenses?.date)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {paddingValues->
        Column(
            modifier = Modifier
                .imePadding()
                .padding(paddingValues)
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Detalhes",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .background(Purple40, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            OutlinedTextField(
                value = expenseName,
                onValueChange = { expenseName = it },
                label = { Text("Nome da despesa") },
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
                )
                IconButton(onClick = { expandedCategories = !expandedCategories }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir/Colapsar Lista"
                    )
                }
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
                        .background(
                            Color(android.graphics.Color.parseColor(selectedColor)),
                            shape = CircleShape
                        )
                        .padding(8.dp)
                )
                IconButton(onClick = { expandedColors = !expandedColors }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expandir/Colapsar Lista"
                    )
                }
            }

            DatePickerContent(date = date, onDateChange = { date = it })


            Spacer(modifier = Modifier.height(16.dp))

            if (expandedCategories) {
                DropMenuCategories()
            }
            if (expandedColors) {
                DropMenuColors(onColorSelected = { colorHex ->
                    selectedColor = colorHex
                })
            }

            Button(
                onClick = {
                    if (
                        expenseName.isEmpty() ||
                        expenseValue.isEmpty() ||
                        newCategoryName.isEmpty() ||
                        selectedColor.isEmpty() ||
                        date.isEmpty()
                    ){
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Preencha todos os campos")
                        }
                    } else{
                        expenseDetailViewModel.editAndSaveExpense(
                            expenseName,
                            expenseValue,
                            newCategoryName,
                            expenseId.toString(),
                            selectedColor,
                            expenseDetailViewModel.convertDateToTimestamp(date)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonColors(
                    containerColor = Purple20,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                ),
            ) {
                Text(
                    text = "Salvar", fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        }

    }

}

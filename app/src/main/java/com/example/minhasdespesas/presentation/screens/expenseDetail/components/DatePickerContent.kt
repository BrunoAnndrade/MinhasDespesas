package com.example.minhasdespesas.presentation.screens.expenseDetail.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.presentation.screens.expenseDetail.ExpenseDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.format.FormatStringsInDatetimeFormats

@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun DatePickerContent(
    date:String,
    onDateChange: (String) -> Unit,
    expenseDetailViewModel: ExpenseDetailViewModel = hiltViewModel()
){
    var openDataPicker by remember { mutableStateOf(false) }
    val state = rememberDatePickerState()

    Row {
        OutlinedTextField(
            value = date,
            onValueChange = {},
            label = { Text("Data") },
            colors = TextFieldDefaults.colors(
                errorPlaceholderColor = Color.Red,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            interactionSource = remember {
                MutableInteractionSource()
            }.also {
                LaunchedEffect(it) {
                    it.interactions.collectLatest { interaction ->
                        if (interaction is PressInteraction.Release) {
                            openDataPicker = true
                        }
                    }
                }
            },
            readOnly = true
        )
        IconButton(onClick = { openDataPicker = true }) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "calendar")
        }
        if(openDataPicker) {
            DatePickerDialog(
                onDismissRequest = { openDataPicker = false },
                confirmButton = {
                    Button(onClick = {
                       val selectedDate =
                           state.selectedDateMillis.let {
                               expenseDetailViewModel.convertMillisToFormattedDate(it)
                           }
                            onDateChange(selectedDate)

                        openDataPicker = false

                    }) {
                        Text(text = "Ok")
                    }
                }
            ) {
                DatePicker(state = state)
            }
        }
    }
}
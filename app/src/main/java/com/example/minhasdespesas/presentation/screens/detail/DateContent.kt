package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
@Composable
fun DateContent(){

    var date by rememberSaveable { mutableStateOf("") }
    val state = rememberDatePickerState()
    var openDataPicker by remember { mutableStateOf(false) }

    Row {
        TextField(
            value = date,
            onValueChange = {},
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
        AnimatedVisibility(openDataPicker) {
            DatePickerDialog(
                onDismissRequest = { openDataPicker = false },
                confirmButton = {
                    Button(onClick = {
                        state.selectedDateMillis?.let { millis->
                            date = Instant
                                .fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.UTC)
                                .date.format(LocalDate.Format {
                                    byUnicodePattern("dd/MM/yyyy")
                                })
                        }
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
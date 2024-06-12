package com.example.minhasdespesas.presentation.screens


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.minhasdespesas.presentation.ExpenseViewModel

import com.example.minhasdespesas.ui.theme.MinhasDespesasTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel:ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasDespesasTheme{

                val state by viewModel.expensesState.collectAsState()

                ExpensesScreen(state = state, onEvent = viewModel::onEvent)


            }

        }
    }
}




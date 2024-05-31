package com.example.minhasdespesas.Presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.despesasdescomplicadas.Presentation.ExpenseViewModel
import com.example.despesasdescomplicadas.Presentation.ExpensesScreen

import com.example.minhasdespesas.ui.theme.MinhasDespesasTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModel.getVMFactory(application)
    }

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




package com.example.minhasdespesas.Presentation


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.despesasdescomplicadas.Presentation.DespesaViewModel
import com.example.despesasdescomplicadas.Presentation.DespesasScreen

import com.example.minhasdespesas.ui.theme.MinhasDespesasTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DespesaViewModel by viewModels {
        DespesaViewModel.getVMFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MinhasDespesasTheme{

                val state by viewModel.despesaState.collectAsState()

                DespesasScreen(state = state, onEvent = viewModel::onEvent)


            }

        }
    }
}




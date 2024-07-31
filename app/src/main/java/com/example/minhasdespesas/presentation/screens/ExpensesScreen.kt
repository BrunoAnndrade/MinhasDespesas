package com.example.minhasdespesas.presentation.screens


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.presentation.ExpenseViewModel
import com.example.minhasdespesas.R
import com.example.minhasdespesas.presentation.BudgetViewModel
import com.example.minhasdespesas.presentation.CategoryViewModel
import com.example.minhasdespesas.presentation.ExpenseDetailViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    navController: NavHostController,
) {
    var showSheet by remember { mutableStateOf(false) }
    val categoryViewModel: CategoryViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Black
                ), title = {
                    Text(
                        text = "Minhas Despesas",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                })
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {showSheet = true}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add despesa"
                )
            }
        },
        modifier = Modifier.padding(16.dp)

    ) { innerPadding ->

        if (showSheet) {
            ExpenseBottomSheet(
                navController,
                onDismiss = {coroutineScope.launch {showSheet = false}}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                ) {
                    BudgetScreen()
                    ExpenseListCard(navController)
                }
            }
        }
    }
}











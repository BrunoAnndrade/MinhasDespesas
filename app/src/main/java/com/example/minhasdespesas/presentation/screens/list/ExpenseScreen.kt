package com.example.minhasdespesas.presentation.screens.list


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.minhasdespesas.presentation.screens.category.CategoryViewModel
import com.example.minhasdespesas.presentation.screens.budget.BudgetScreen
import com.example.minhasdespesas.presentation.screens.category.CategoryScreen
import com.example.minhasdespesas.presentation.screens.detail.ExpenseBottomSheet
import com.example.minhasdespesas.ui.theme.Purple40
import com.example.minhasdespesas.ui.theme.PurpleLight
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
                    containerColor = Purple40
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(
                        text = "Minhas Despesas",
                        fontSize = 20.sp,
                        color = Color.White,
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showSheet = true },
                containerColor = PurpleLight,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add despesa",
                    tint = Color.White,
                )
            }
        },
    ) { innerPadding ->

        if (showSheet) {
            ExpenseBottomSheet(
                navController,
                onDismiss = { coroutineScope.launch { showSheet = false } }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                BudgetScreen()

            }
            Spacer(modifier = Modifier.height(20.dp))
            Spacer(
                modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                ,
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.Top,
                ) {
                    CategoryScreen()
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                    ) {
                        ExpenseListCard(navController)
                    }
                }
            }
        }
    }
}











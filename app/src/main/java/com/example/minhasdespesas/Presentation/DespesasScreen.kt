package com.example.despesasdescomplicadas.Presentation


import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.despesasdescomplicadas.DespesaEvent
import com.example.despesasdescomplicadas.DespesaState

import com.example.despesasdescomplicadas.SortType
import com.example.minhasdespesas.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DespesasScreen(
    state: DespesaState,
    onEvent: (DespesaEvent) -> Unit


) {

    val viewModel: DespesaViewModel = viewModel()

    val despesasState = viewModel.myOrcamento?.collectAsState()?.value ?: 0.0

    Scaffold(
        topBar = {
            TopAppBar(

                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Black
                ), title = {
                    Text(
                        text = "DESPESAS DESCOMPLICADAS",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(DespesaEvent.DespesaShowDialog)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add despesa"
                )
            }

        },
        modifier = Modifier.padding(16.dp)

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),

                ) {


                if (state.isAddingMoney) {
                    MoneyDialog(state = state, onEvent = onEvent)
                }


                Row(

                ) {


                    Text(
                        text = "Orçamento ",
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)


                    )
                    Text(
                        text = "R$ $despesasState "
                            ?: "0",
                        style = TextStyle.Default.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .align(Alignment.CenterVertically)
                    )



                    Button(
                        onClick = { onEvent(DespesaEvent.MoneyShowDialog) },
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent

                        ),

                        ) {

                        Image(
                            painter = painterResource(id = R.drawable.ic_money4),
                            contentDescription = "money",
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Top),


                            )


                    }
                }





                if (state.isAddingDespesa) {
                    DespesaDialog(state = state, onEvent = onEvent)
                }

                LazyColumn(
                    modifier = Modifier.padding(vertical = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SortType.values().forEach { sortType ->
                                Row(
                                    modifier = Modifier
                                        .clickable {
                                            onEvent(DespesaEvent.SortDespesas(sortType))
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    RadioButton(
                                        selected = state.sortType == sortType,
                                        onClick = {
                                            onEvent(DespesaEvent.SortDespesas(sortType))
                                        }
                                    )
                                    Text(text = sortType.name)


                                }
                            }

                        }
                    }

                    items(state.despesas) { despesa ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = despesa.title + despesa.category,
                                    fontSize = 20.sp
                                )
                                Text(
                                    text = "${despesa.valor}",
                                    fontSize = 12.sp
                                )
                            }

                            IconButton(
                                onClick = { onEvent(DespesaEvent.DeleteDespesa(despesa)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "delete despesa"
                                )

                            }

                        }

                    }


                }
            }

        }


    }
}






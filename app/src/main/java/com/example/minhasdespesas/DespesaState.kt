package com.example.despesasdescomplicadas

import com.example.despesasdescomplicadas.Data.Despesa


data class DespesaState(
    val despesas: List<Despesa> = emptyList(),
    val title: String = "",
    val valor: String = "",
    val category: String = "",
    val dinheiro:String = "",
    val isAddingDespesa: Boolean = false,
    val isAddingMoney:Boolean = false,
    val sortType: SortType = SortType.TITLE
)
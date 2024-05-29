package com.example.despesasdescomplicadas

import com.example.despesasdescomplicadas.Data.Despesa

sealed interface DespesaEvent {

    object SaveDespesa : DespesaEvent
    object SaveMoney:DespesaEvent
    data class SetName(val name: String) : DespesaEvent
    data class SetValor(val valor: String) : DespesaEvent
    data class SetCategory(val category: String) : DespesaEvent
    data class SetMoney(val money: String):DespesaEvent
    object DespesaShowDialog : DespesaEvent
    object MoneyShowDialog : DespesaEvent
    object HideDialog : DespesaEvent
    data class SortDespesas(val sortType: SortType) : DespesaEvent
    data class DeleteDespesa(val despesa: Despesa) : DespesaEvent
}
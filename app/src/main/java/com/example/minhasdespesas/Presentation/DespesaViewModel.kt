package com.example.despesasdescomplicadas.Presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.despesasdescomplicadas.Data.Despesa
import com.example.despesasdescomplicadas.Data.DespesaDao
import com.example.despesasdescomplicadas.Data.Orcamento
import com.example.despesasdescomplicadas.DespesaEvent
import com.example.despesasdescomplicadas.DespesaState
import com.example.despesasdescomplicadas.DespesasApplication
import com.example.despesasdescomplicadas.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DespesaViewModel(val despesaDao: DespesaDao) : ViewModel() {


    private val _sortType = MutableStateFlow(SortType.TITLE)

    private val _myOrcamento = MutableStateFlow("")
    val myOrcamento: StateFlow<String> = _myOrcamento.asStateFlow()

    init {
        viewModelScope.launch {
            despesaDao.getOrcamentoFlow().collect { orcamentoValue ->
                _myOrcamento.value = orcamentoValue

            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private val _despesas = _sortType
        .flatMapLatest { sortType ->

            when (sortType) {
                SortType.TITLE -> this.despesaDao.getAllDespesaByname()
                SortType.VALOR -> this.despesaDao.getAllDespesaByValor()
                SortType.CATEGORY -> this.despesaDao.getAllDespesaByCategory()
            }

            // Use stateIn to create state Flow with data with the data resulting from the mapping
            // operation (flatMapLatest).
            // ViewModelScope because when flow is canceled appropriately when the ViewModel is deactivated.
            // SharingStarted specifies that stream collection should begin when an observer is subscribed
            // to the stream and end when all observers are unsubscribed.
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _despesasState = MutableStateFlow(DespesaState())
    val despesaState =
        combine(_despesasState, _sortType, _despesas) { despesaState, sortType, despesas ->
            despesaState.copy(
                despesas = despesas,
                sortType = sortType,

                )

        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DespesaState())


    fun onEvent(event: DespesaEvent) {
        val dinheiro = despesaState.value.dinheiro.toDoubleOrNull() ?: 0.0

        when (event) {
            is DespesaEvent.DeleteDespesa -> {

                viewModelScope.launch {

                    val despesa = despesaDao.getDespesaById(event.despesa.id)

                    if (despesa != null){
                        despesaDao.deleteDespesa(despesa)

                        // Update the orcamento
                        val orcamentoFlow = despesaDao.getOrcamentoFlow()
                        val orcamentoString = orcamentoFlow.firstOrNull()?.toString()
                        val orcamentoDouble = orcamentoString?.toDoubleOrNull() ?: 0.0
                        val valorDespesa = despesa.valor.toDoubleOrNull() ?: 0.0

                        // Calculate the new budget
                        val novoOrcamento = orcamentoDouble + valorDespesa

                        // Update the budget in the database
                        val novoOrcamentoObj = Orcamento(orcamento = novoOrcamento.toString())
                        despesaDao.updateOrçamento(novoOrcamentoObj)

                    }
                }
            }

            DespesaEvent.HideDialog -> {
                _despesasState.update { it.copy(isAddingDespesa = false) }
                _despesasState.update { it.copy(isAddingMoney = false) }
            }

            DespesaEvent.DespesaShowDialog -> {
                _despesasState.update { it.copy(isAddingDespesa = true) }

            }

            DespesaEvent.MoneyShowDialog -> {
                _despesasState.update { it.copy(isAddingMoney = true) }
            }

            DespesaEvent.SaveDespesa -> {
                val title = despesaState.value.title
                val valor = despesaState.value.valor
                val category = despesaState.value.category


                if (title.isBlank() || valor.isBlank() || category.isBlank()) {
                    return
                }

                val despesa = Despesa(
                    title = title,
                    valor = valor,
                    category = category
                )





                viewModelScope.launch {
                    despesaDao.upsertDespesa(despesa)

                    val orcamentoFlow = despesaDao.getOrcamentoFlow()
                    val orcamentoString = orcamentoFlow.firstOrNull()?.toString()

                    // Convert String to double
                    val valorDespesa = despesa.valor.toDoubleOrNull() ?: 0.0
                    val orcamentoDouble = orcamentoString?.toDoubleOrNull() ?: 0.0

                    // Calculate the new budget
                    val novoOrçamento = orcamentoDouble - valorDespesa

                    // Create a new Orçamento object with the updated value
                    val novoOrçamentoObj = Orcamento(orcamento = novoOrçamento.toString())

                    // Update the budget in the database
                    despesaDao.updateOrçamento(novoOrçamentoObj)
                }

                _despesasState.update {
                    it.copy(
                        isAddingDespesa = false,
                        title = "",
                        valor = "",
                        category = "",

                        )
                }
            }

            DespesaEvent.SaveMoney -> {

                viewModelScope.launch {

                    val orcamentoFlow = despesaDao.getOrcamentoFlow()
                    val orcamentoDouble = orcamentoFlow.firstOrNull()?.toDoubleOrNull() ?: 0.0

                    //calculate new budget
                    val novoOrcamento = orcamentoDouble + dinheiro

                    // create a new Orcamento objet with the updated value
                    val novoOrcamentoObj = Orcamento(id = 1, orcamento = novoOrcamento.toString())

                    //update budget into database
                    despesaDao.insertOrçamento(novoOrcamentoObj)

                    _despesasState.update {
                        it.copy(
                            isAddingMoney = false,
                            dinheiro = "",

                            )
                    }
                }
            }


            is DespesaEvent.SetCategory -> {
                _despesasState.update { it.copy(category = event.category) }
            }

            is DespesaEvent.SetName -> {
                _despesasState.update { it.copy(title = event.name) }
            }

            is DespesaEvent.SetValor -> {
                _despesasState.update { it.copy(valor = event.valor) }
            }

            is DespesaEvent.SortDespesas -> {
                _sortType.value = event.sortType
            }

            is DespesaEvent.SetMoney -> {
                _despesasState.update {
                    it.copy(dinheiro = event.money)
                }
            }
        }
    }


    /*para utilizar viewmModel com by viewmodel e poder rotarcionar a tela sem perder o que foi
       digitado*/
    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as DespesasApplication).getAppDataBase()
            val dao = dataBaseInstance.despesaDao()
            val factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return DespesaViewModel(dao) as T
                }
            }
            return factory
        }
    }
}
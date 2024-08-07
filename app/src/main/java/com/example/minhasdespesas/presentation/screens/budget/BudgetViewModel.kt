package com.example.minhasdespesas.presentation.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.repository.BudgetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
):ViewModel() {

    private val _myBudget = MutableStateFlow("")
    val myBudget: StateFlow<String> = _myBudget.asStateFlow()

    init {
        viewModelScope.launch {
            getBudget()
        }
    }

    private suspend fun getBudget() {
        budgetRepository.getBudgetFlow().collect{ budget ->
            _myBudget.value = budget
        }
    }

    suspend fun saveMoney(myMoney: Double){

        val budgetFlow = budgetRepository.getBudgetFlow()
        val budgetDouble = budgetFlow.firstOrNull()?.toDouble()
        val newBudget = budgetDouble?.plus(myMoney)
        val newBudgetObj = BudgetEntity(budget = newBudget.toString())

        budgetRepository.insertBudget(newBudgetObj)
        budgetRepository.updateBudget(newBudgetObj)
    }


}
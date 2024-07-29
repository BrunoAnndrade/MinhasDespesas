package com.example.minhasdespesas.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
):ViewModel() {

    private val _expensesUi = MutableStateFlow<ExpenseEntity?>(null)
    val expensesUi: StateFlow<ExpenseEntity?> = _expensesUi


    fun fetchExpenseDetail(moviesId: String?){
        viewModelScope.launch(Dispatchers.IO) {
            val data = moviesId?.let { expenseRepository.getExpenseById(it) }
            _expensesUi.value = data?.id?.let {
                _expensesUi.value?.copy(
                    id = data.id,
                    title = data.title,
                    expenseValue = data.expenseValue,
                    category = data.category
                )
            }
        }

    }
}
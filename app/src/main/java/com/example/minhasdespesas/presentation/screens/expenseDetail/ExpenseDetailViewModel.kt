package com.example.minhasdespesas.presentation.screens.expenseDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import com.example.minhasdespesas.data.repository.BudgetRepository
import com.example.minhasdespesas.data.repository.CategoryRepository
import com.example.minhasdespesas.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _expensesUi = MutableStateFlow<ExpenseEntity?>(null)
    val expensesUi: StateFlow<ExpenseEntity?> = _expensesUi

    fun fetchExpenseDetail(moviesId: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = moviesId?.let { expenseRepository.getExpenseById(it) }
            _expensesUi.value = data
        }
    }
    @OptIn(FormatStringsInDatetimeFormats::class)
    fun convertDateToTimestamp(dateString: String): Long {
        return LocalDate.parse(
            dateString,LocalDate.Format { byUnicodePattern("dd/MM/yyyy")}
        )
                .atStartOfDayIn(TimeZone.UTC)
                .toEpochMilliseconds()
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun convertMillisToFormattedDate(millis: Long?): String {
        return millis?.let {
            Instant.fromEpochMilliseconds(it)
                .toLocalDateTime(TimeZone.UTC)
                .date.format(LocalDate.Format { byUnicodePattern("dd/MM/yyyy") })
        } ?: ""
    }

    fun editAndSaveExpense(
        expenseName: String,
        expenseValue: Double,
        category: String,
        id: String,
        selectedColor: String,
        date: Long?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val categories = CategoryEntity(name = category)
            val expenses = ExpenseEntity(
                title = expenseName,
                expenseValue = expenseValue,
                category = category,
                id = id.toInt(),
                color = selectedColor,
                date = date
            )
            categoryRepository.insertCategory(categories)
            expenseRepository.upsertExpense(expenses)
        }
    }
}
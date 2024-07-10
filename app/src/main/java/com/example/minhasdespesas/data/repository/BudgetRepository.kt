package com.example.minhasdespesas.data.repository

import com.example.minhasdespesas.data.BudgetDao
import com.example.minhasdespesas.data.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BudgetRepository @Inject constructor(
    private val budgetDao: BudgetDao
) {

    fun getBudgetFlow(): Flow<String> = budgetDao.getBudgetFlow()
    suspend fun insertBudget(budget: BudgetEntity) = budgetDao.insertBudget(budget)
    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)
}
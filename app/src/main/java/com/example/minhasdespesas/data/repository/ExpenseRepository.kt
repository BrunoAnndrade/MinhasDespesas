package com.example.minhasdespesas.data.repository

import com.example.minhasdespesas.data.dao.BudgetDao
import com.example.minhasdespesas.data.dao.ExpenseDao
import com.example.minhasdespesas.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao,
) {
    //Expenses
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()
    suspend fun getAllExpenseByValue(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenseByValue()
    fun getAllExpenseByCategory(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenseByCategory()
    suspend fun getExpenseById(expenseId:String): ExpenseEntity? = expenseDao.getExpenseById(expenseId)
    suspend fun upsertExpense(expense: ExpenseEntity) = expenseDao.upsertExpense(expense)
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    suspend fun deleteExpenseById(expenseId: Int):Int = expenseDao.deleteExpenseById(expenseId)







}
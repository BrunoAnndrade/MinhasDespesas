package com.example.minhasdespesas.data.repository

import com.example.minhasdespesas.data.BudgetDao
import com.example.minhasdespesas.data.CategoryDao
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.ExpenseDao
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseRepository @Inject constructor(
    private val expenseDao: ExpenseDao,
    private val budgetDao: BudgetDao,
    private val categoryDao: CategoryDao
) {
    //Expenses
    suspend fun getAllExpenseByName(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenseByName()
    suspend fun getAllExpenseByValue(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenseByValue()
    fun getAllExpenseByCategory(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenseByCategory()
    suspend fun getExpenseById(expenseId:Int): ExpenseEntity? = expenseDao.getExpenseById(expenseId)
    suspend fun upsertExpense(expense: ExpenseEntity) = expenseDao.upsertExpense(expense)
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    suspend fun deleteExpenseById(expenseId: Int):Int = expenseDao.deleteExpenseById(expenseId)

    // Categories
    suspend fun getAllCategory():Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: CategoryEntity)  = categoryDao.insertCategory(category)


    // Budget
    fun getBudgetFlow():Flow<String> = budgetDao.getBudgetFlow()
    suspend fun insertBudget(budget: BudgetEntity) = budgetDao.insertBudget(budget)
    suspend fun updateBudget(budget: BudgetEntity) = budgetDao.updateBudget(budget)



}
package com.example.despesasdescomplicadas.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT budget FROM BudgetEntity WHERE id = 1")
    fun getBudgetFlow(): Flow<String>


    @Query("SELECT * FROM ExpenseEntity ORDER BY title ASC")
    fun getAllExpenseByName(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM ExpenseEntity ORDER BY category ASC")
    fun getAllExpenseByCategory(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM ExpenseEntity WHERE id = :expenseId LIMIT 1")
    suspend fun getExpenseById(expenseId: Int): ExpenseEntity?

    @Query("SELECT * FROM ExpenseEntity ORDER BY expenseValue ASC")
    fun getAllExpenseByValue(): Flow<List<ExpenseEntity>>

    @Upsert
    suspend fun upsertExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgetEntity: BudgetEntity)
    @Update
    suspend fun updateBudget(budgetEntity:BudgetEntity)
}

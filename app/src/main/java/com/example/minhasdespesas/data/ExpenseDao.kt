package com.example.minhasdespesas.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.minhasdespesas.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses ORDER BY id ASC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    @Query("SELECT * FROM expenses ORDER BY category ASC")
    fun getAllExpenseByCategory(): Flow<List<ExpenseEntity>>
    @Query("SELECT * FROM expenses WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: String): ExpenseEntity?
    @Query("SELECT * FROM expenses ORDER BY expenseValue ASC")
    fun getAllExpenseByValue(): Flow<List<ExpenseEntity>>
    @Upsert
    suspend fun upsertExpense(expense: ExpenseEntity)
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    @Query("DELETE FROM expenses WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Int):Int

}

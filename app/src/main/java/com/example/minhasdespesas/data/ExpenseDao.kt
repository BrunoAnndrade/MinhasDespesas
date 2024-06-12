package com.example.minhasdespesas.data

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

    @Query("SELECT * FROM ExpenseEntity ORDER BY title ASC")
    fun getAllExpenseByName(): Flow<List<ExpenseEntity>>
    @Query("SELECT * FROM ExpenseEntity ORDER BY category ASC")
    fun getAllExpenseByCategory(): Flow<List<ExpenseEntity>>
    @Query("SELECT * FROM ExpenseEntity WHERE id = :expenseId")
    suspend fun getExpenseById(expenseId: Int): ExpenseEntity?
    @Query("SELECT * FROM ExpenseEntity ORDER BY expenseValue ASC")
    fun getAllExpenseByValue(): Flow<List<ExpenseEntity>>
    @Upsert
    suspend fun upsertExpense(expense: ExpenseEntity)
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    @Query("DELETE FROM ExpenseEntity WHERE id = :expenseId")
    suspend fun deleteExpenseById(expenseId: Int):Int

}

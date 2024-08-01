package com.example.minhasdespesas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.minhasdespesas.data.entity.BudgetEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BudgetDao {
    @Query("SELECT budget FROM BudgetEntity WHERE id = 1")
    fun getBudgetFlow(): Flow<String>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budgetEntity: BudgetEntity)
    @Update
    suspend fun updateBudget(budgetEntity: BudgetEntity)
}
package com.example.minhasdespesas.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class, BudgetEntity::class, CategoryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao

}

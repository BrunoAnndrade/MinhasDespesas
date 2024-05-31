package com.example.despesasdescomplicadas.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ExpenseEntity::class, BudgetEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

}

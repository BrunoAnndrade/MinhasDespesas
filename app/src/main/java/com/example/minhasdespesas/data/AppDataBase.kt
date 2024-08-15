package com.example.minhasdespesas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.minhasdespesas.data.dao.BudgetDao
import com.example.minhasdespesas.data.dao.CategoryDao
import com.example.minhasdespesas.data.dao.ExpenseDao
import com.example.minhasdespesas.data.entity.BudgetEntity
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.entity.ExpenseEntity
import androidx.room.AutoMigration
import androidx.room.migration.AutoMigrationSpec


@Database(
    entities = [
        ExpenseEntity::class,
        BudgetEntity::class,
        CategoryEntity::class,
    ],
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 3, to = 4)
    ]

)
abstract class AppDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun budgetDao(): BudgetDao
    abstract fun categoryDao(): CategoryDao
}

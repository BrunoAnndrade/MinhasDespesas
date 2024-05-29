package com.example.despesasdescomplicadas.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Despesa::class, Orcamento::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun despesaDao(): DespesaDao

}

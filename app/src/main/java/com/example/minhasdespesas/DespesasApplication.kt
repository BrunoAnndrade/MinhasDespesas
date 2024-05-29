package com.example.despesasdescomplicadas

import android.app.Application
import androidx.room.Room
import com.example.despesasdescomplicadas.Data.AppDataBase

class DespesasApplication : Application() {

    private lateinit var dataBase: AppDataBase
    override fun onCreate() {
        super.onCreate()

        dataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "Despesas-DataBase"
        ).build()
    }

    fun getAppDataBase(): AppDataBase {
        return dataBase
    }
}
package com.example.minhasdespesas.Domain

import android.content.Context
import androidx.room.Room
import com.example.minhasdespesas.data.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideExpenseDao(appDataBase: AppDataBase) = appDataBase.expenseDao()

    @Provides
    @Singleton
    fun provideCategoryDao(appDataBase: AppDataBase) = appDataBase.categoryDao()

    @Provides
    @Singleton
    fun provideBudgetDao(appDataBase: AppDataBase) = appDataBase.budgetDao()

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "APP_DATABASE"
    ).build()


}
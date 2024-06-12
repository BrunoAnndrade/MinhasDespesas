package com.example.minhasdespesas

import android.app.Application
import androidx.room.Room
import com.example.minhasdespesas.data.AppDataBase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ExpensesApplication : Application()
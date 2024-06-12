package com.example.minhasdespesas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BudgetEntity(
    @PrimaryKey
    val id: Int = 1,
    val budget:String = "0"
)

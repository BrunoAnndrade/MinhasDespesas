package com.example.despesasdescomplicadas.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Despesa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val valor: String,
    val category: String
)



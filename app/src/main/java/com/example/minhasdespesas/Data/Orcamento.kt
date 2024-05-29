package com.example.despesasdescomplicadas.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Orcamento(
    @PrimaryKey
    val id: Int = 1,
    val orcamento:String?
)

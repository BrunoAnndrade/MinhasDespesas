package com.example.minhasdespesas.data.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val colorHex: String
)
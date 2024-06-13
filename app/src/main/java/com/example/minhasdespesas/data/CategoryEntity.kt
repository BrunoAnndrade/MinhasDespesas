package com.example.minhasdespesas.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CategoryEntity(
    @PrimaryKey
    val name:String,
    val isSelected:Boolean,
    val color:Int
)

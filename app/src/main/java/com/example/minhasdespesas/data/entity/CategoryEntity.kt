package com.example.minhasdespesas.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "categories",indices = [Index(value = ["name"], unique = true)])
data class CategoryEntity(
    @PrimaryKey
    val name:String,
)

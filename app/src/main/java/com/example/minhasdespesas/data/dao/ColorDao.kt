package com.example.minhasdespesas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.minhasdespesas.data.entity.ColorEntity

@Dao
interface ColorDao {
    @Insert
    suspend fun insertColor(color: ColorEntity)

    @Query("SELECT * FROM colors")
    suspend fun getAllColors(): List<ColorEntity>
}
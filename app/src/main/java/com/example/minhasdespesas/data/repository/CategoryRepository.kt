package com.example.minhasdespesas.data.repository

import com.example.minhasdespesas.data.CategoryDao
import com.example.minhasdespesas.data.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val dao: CategoryDao
) {

    fun getCategories(): Flow<List<CategoryEntity>> = dao.getAllCategories()

    suspend fun insertCategory(category: CategoryEntity) {
        dao.insertCategory(category)
    }

    suspend fun updateCategory(category: CategoryEntity) {
        dao.updateCategory(category)
    }

    suspend fun deleteCategory(category: CategoryEntity) {
        dao.deleteCategory(category)
    }
}
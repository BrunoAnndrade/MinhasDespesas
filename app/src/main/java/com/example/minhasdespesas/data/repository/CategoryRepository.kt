package com.example.minhasdespesas.data.repository

import com.example.minhasdespesas.data.dao.CategoryDao
import com.example.minhasdespesas.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    // Categories
    fun getAllCategory(): Flow<List<CategoryEntity>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: CategoryEntity)  = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: CategoryEntity) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: CategoryEntity) = categoryDao.deleteCategory(category)
    suspend fun getCategoryByName(name: String): CategoryEntity? = categoryDao.getCategoryByName(name)
}
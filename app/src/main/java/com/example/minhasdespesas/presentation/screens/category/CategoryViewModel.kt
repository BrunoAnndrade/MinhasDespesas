package com.example.minhasdespesas.presentation.screens.category

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val availableColors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta,
        Color.Gray,
        Color.Black
    )

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            getAllCategories()
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategory().collect{
                val allCategory = CategoryEntity(name = "Todos")
                _categories.value = it + allCategory
            }
        }
    }

    fun saveCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
            updateCategory(category)
        }
    }

    private fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }
}





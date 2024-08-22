package com.example.minhasdespesas.presentation.screens.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.presentation.screens.list.ExpenseViewModel
import com.example.minhasdespesas.ui.theme.Purple20
import com.example.minhasdespesas.ui.theme.PurpleLight


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    expenseViewModel: ExpenseViewModel = hiltViewModel(),
) {
    val categories by categoryViewModel.categories.collectAsState()
    val showDeleteDialog = remember { mutableStateOf(false) }
    var selectedCategory = remember { (CategoryEntity("")) }

    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.White)
    ) {
        Row {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                reverseLayout = true
            ) {
                items(categories) { category ->
                    Text(
                        text = category.name,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Purple20, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .combinedClickable(
                                onClick = {
                                    expenseViewModel.filterExpenseByCategoryName(category.name)
                                },
                                onLongClick = {
                                    selectedCategory  = category
                                    showDeleteDialog.value = true
                                },
                            ),
                    )

                    if (showDeleteDialog.value) {
                        DialogDeleteCategory(
                            onDismiss = { showDeleteDialog.value = false },
                            category = selectedCategory
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {


}
package com.example.minhasdespesas.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.minhasdespesas.presentation.CategoryViewModel
import org.openjdk.tools.javac.jvm.Items


@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel = viewModel()
) {
    val categories by categoryViewModel.categories.collectAsState()

    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
    ) {
        Row {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    Text(text = category.name)
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()

}
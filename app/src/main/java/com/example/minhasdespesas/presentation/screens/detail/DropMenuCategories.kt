package com.example.minhasdespesas.presentation.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.presentation.screens.category.CategoryViewModel
import com.example.minhasdespesas.ui.theme.Purple20

@Composable
fun DropMenuCategories(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
){
    val categories by categoryViewModel.categories.collectAsState()
    var newCategoryName by rememberSaveable { mutableStateOf("") }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(
            4.dp,
            alignment = CenterHorizontally
        ),
        verticalItemSpacing = 4.dp,

        modifier = Modifier
            .fillMaxSize()
    ) {
        items(categories) { category ->
            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple20, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        newCategoryName = category.name
                    }
                    .padding(8.dp),
                color = Color.White,
            )
        }
    }
}
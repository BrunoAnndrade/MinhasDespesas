package com.example.minhasdespesas.presentation.screens.detail.dropMenu
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DropMenuColors(
    onColorSelected: (String) -> Unit,
){
    val listColors = listOf(
        "#E57373",
        "#F06292",
        "#BA68C8",
        "#9575CD",
        "#7986CB",
        "#64B5F6",
        "#4FC3F7",
        "#4DD0E1",
        "#4DB6AC",
        "#81C784",
        "#AED581",
        "#FF8A65",
        "#FFA726"
    )

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(7),
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = CenterHorizontally
        ),
        verticalItemSpacing = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)

    ) {
        items(listColors.size){ index ->

            val color = Color(android.graphics.Color.parseColor(listColors[index]))

            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(40.dp)
                    .background(color, shape = CircleShape)
                    .clickable {
                        onColorSelected(listColors[index])
                    }
                    .padding(8.dp)
            )
        }
    }
}

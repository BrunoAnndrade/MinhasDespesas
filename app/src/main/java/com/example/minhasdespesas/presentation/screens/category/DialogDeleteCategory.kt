package com.example.minhasdespesas.presentation.screens.category

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.minhasdespesas.data.entity.CategoryEntity
import com.example.minhasdespesas.ui.theme.Purple40

@Composable
fun DialogDeleteCategory(
    onDismiss: () -> Unit,
    category:CategoryEntity,
    categoryViewModel: CategoryViewModel = hiltViewModel()

){

    AlertDialog(
        title = {
            Text(
                text = "Deseja deletar a categoria ${category.name}?",
                color = Purple40,
                textAlign =  TextAlign.Start
            )
        },
        onDismissRequest = {
            onDismiss()
        },

        confirmButton = {
            Button(
                onClick = {
                    categoryViewModel.deleteCategory(category)
                }
            ) {
                Text(text = "Sim")
            }

        },
        dismissButton = {
            Button(
                onClick = { onDismiss() }
            ) {
                Text(text = "Não")
            }
        },
    )
}

@Preview
@Composable
fun DialogDeleteCategoryPreview(){
    DialogDeleteCategory(
        onDismiss = {},
        category = CategoryEntity(name = "Alimentação")
    )

}
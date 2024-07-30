package com.example.minhasdespesas.presentation.screens

import com.example.minhasdespesas.data.entity.ExpenseEntity



class ExpensesExemplos (){

    companion object{
        val expenseList = listOf(
            ExpenseEntity(
                title = "Mercado",
                expenseValue = "100.00",
                category = "Alimentação"
            ),
            ExpenseEntity(
                title = "Cinema",
                expenseValue = "50.00",
                category = "Entretenimento"
            ),
            ExpenseEntity(
                title = "Restaurante",
                expenseValue = "80.00",
                category = "Alimentação"
            )
        )
    }

}
package com.blach.unilife.model.data.expenses

data class ExpenseDTO(
    val category: String = "",
    val amount: Double = 0.0,
    val description: String? = null,
    val date: String = ""
)

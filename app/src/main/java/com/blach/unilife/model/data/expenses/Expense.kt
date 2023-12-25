package com.blach.unilife.model.data.expenses

import java.time.LocalDate

data class Expense(
    val id: String,
    val category: ExpenseCategory,
    val amount: Double,
    val description: String?,
    val date: LocalDate
)

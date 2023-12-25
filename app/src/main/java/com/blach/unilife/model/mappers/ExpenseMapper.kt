package com.blach.unilife.model.mappers

import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseCategory
import com.blach.unilife.model.data.expenses.ExpenseDTO
import java.time.LocalDate

object ExpenseMapper {
    fun fromDTO(dto: ExpenseDTO, id: String): Expense {
        return Expense(
            id = id,
            category = ExpenseCategory.valueOf(dto.category),
            amount = dto.amount,
            description = dto.description,
            date = LocalDate.parse(dto.date)
        )
    }

    fun toDTO(model: Expense): ExpenseDTO {
        return ExpenseDTO(
            category = model.category.toString(),
            amount = model.amount,
            description = model.description,
            date = model.date.toString()
        )
    }
}
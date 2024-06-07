package com.blach.unilife.view.data.expenses

import com.blach.unilife.model.data.expenses.ExpenseCategory
import java.time.LocalDate

data class AddOrEditExpenseUIState(
    val category: ExpenseCategory = ExpenseCategory.HOME_AND_BILLS,
    val amount: String = "",
    val description: String? = "",
    val date: LocalDate = LocalDate.now(),
    val isDatePickerDialogOpen: Boolean = false,
    val isCategoryDropDownExpanded: Boolean = false,
    val isDataLoaded: Boolean = false
)

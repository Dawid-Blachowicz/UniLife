package com.blach.unilife.view.data.expenses

import com.blach.unilife.model.data.expenses.ExpenseCategory
import java.time.LocalDate

sealed class AddOrEditExpenseUIEvent{
    data class CategoryChanged (val category: ExpenseCategory): AddOrEditExpenseUIEvent()
    data class AmountChanged(val amount: String): AddOrEditExpenseUIEvent()
    data class DescriptionChanged(val description: String?): AddOrEditExpenseUIEvent()
    data class DateChanged(val date: LocalDate): AddOrEditExpenseUIEvent()
    data class IsDatePickerDialogOpenChanged(val isDatePickerDialogOpen: Boolean): AddOrEditExpenseUIEvent()

    data object SaveButtonClicked: AddOrEditExpenseUIEvent()
    data object ToggleCategoryDropDown: AddOrEditExpenseUIEvent()

}

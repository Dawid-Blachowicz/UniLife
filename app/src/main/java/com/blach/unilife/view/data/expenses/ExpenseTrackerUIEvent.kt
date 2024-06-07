package com.blach.unilife.view.data.expenses

import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseCategory
import java.time.YearMonth

sealed class ExpenseTrackerUIEvent{
    data class LeadExpense(val expense: Expense): ExpenseTrackerUIEvent()
    data class SelectedTabIndexChanged(val selectedTabIndex: Int): ExpenseTrackerUIEvent()
    data class ChartSliceClicked(val category: ExpenseCategory): ExpenseTrackerUIEvent()
    data class CurrentMonthChanged(val currentMonth: YearMonth): ExpenseTrackerUIEvent()

    data object DeleteButtonClicked: ExpenseTrackerUIEvent()
}

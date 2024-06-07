package com.blach.unilife.view.data.expenses

import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.blach.unilife.model.data.expenses.Expense
import java.time.YearMonth

data class ExpenseTrackerUIState(
    val allExpenses: List<Expense> = emptyList(),
    val homeAndBillsExpenses: List<Expense> = emptyList(),
    val foodExpenses: List<Expense> = emptyList(),
    val healthAndBeautyExpenses: List<Expense> = emptyList(),
    val entertainmentAndTravelExpenses: List<Expense> = emptyList(),
    val clothesAndAccessoriesExpenses: List<Expense> = emptyList(),
    val educationExpenses: List<Expense> = emptyList(),
    val transportExpenses: List<Expense> = emptyList(),
    val otherExpenses: List<Expense> = emptyList(),
    val selectedTabIndex: Int = 0,
    val allExpensesSum: Double = 0.0,
    val pieChartData: PieChartData = PieChartData(
        slices = listOf(PieChartData.Slice(
            label = "Brak",
            value = 0f,
            color = Color.LightGray)),
        plotType =  PlotType.Donut),
    val currentMonth: YearMonth = YearMonth.now()
)

package com.blach.unilife.view.components

import android.graphics.Typeface
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.blach.unilife.R
import com.blach.unilife.model.data.expenses.Expense
import com.blach.unilife.model.data.expenses.ExpenseCategory
import com.blach.unilife.model.data.expenses.getCategoryDisplayName
import com.blach.unilife.view.data.expenses.ExpenseTrackerUIEvent
import com.blach.unilife.view.navigation.Routes
import com.blach.unilife.view.utils.DateFormatter
import com.blach.unilife.viewmodels.expenses.ExpenseTrackerViewModel
import java.time.YearMonth

@Composable
fun NoCategoryExpenseItem(
    expense: Expense,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.amount.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
            }
            Text(
                text = expense.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
            )
            Surface(
                color = getCategoryColor(category = expense.category),
                shape = MaterialTheme.shapes.small,
            ) {
                Text(
                    text = getCategoryDisplayName(category = expense.category),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun ExpenseItemWithCategory(
    expense: Expense,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.amount.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
            }
            Text(
                text = expense.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray,
            )
        }
    }
}

@Composable
fun getCategoryColor(category: ExpenseCategory): Color {
    return when(category) {
        ExpenseCategory.HOME_AND_BILLS -> colorResource(id = R.color.color_home_and_bills)
        ExpenseCategory.FOOD -> colorResource(id = R.color.color_food)
        ExpenseCategory.HEALTH_AND_BEAUTY -> colorResource(id = R.color.color_health_and_beauty)
        ExpenseCategory.ENTERTAINMENT_AND_TRAVEL -> colorResource(id = R.color.color_entertainment_and_travel)
        ExpenseCategory.CLOTHES_AND_ACCESSORIES -> colorResource(id = R.color.color_clothes_and_accessories)
        ExpenseCategory.EDUCATION -> colorResource(id = R.color.color_education)
        ExpenseCategory.TRANSPORT -> colorResource(id = R.color.color_transport)
        ExpenseCategory.OTHER -> colorResource(id = R.color.color_other)
    }
}

@Composable
fun ExpenseTabRow(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val tabTitles = listOf(
        stringResource(R.string.category_all_expenses),
        stringResource(id = R.string.category_home_and_bills),
        stringResource(id = R.string.category_food),
        stringResource(id = R.string.category_health_and_beauty),
        stringResource(id = R.string.category_entertainment_and_travel),
        stringResource(id = R.string.category_clothes_and_accessories),
        stringResource(id = R.string.category_education),
        stringResource(id = R.string.category_transport),
        stringResource(id = R.string.category_other),
    )
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp
        ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = index == selectedTabIndex,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

@Composable
fun ExpensesTab(
    navController: NavController,
    viewModel: ExpenseTrackerViewModel,
    expenses: List<Expense>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyColumn {
            items(expenses) {expenseItem ->
                NoCategoryExpenseItem(
                    expense = expenseItem,
                    onClick = {
                        viewModel.onEvent(ExpenseTrackerUIEvent.LeadExpense(expenseItem))
                        navController.navigate("${Routes.NEW_EXPENSE_SCREEN}/${expenseItem.id}")
                    },
                    onDelete = {
                        viewModel.onEvent(ExpenseTrackerUIEvent.LeadExpense(expenseItem))
                        viewModel.onEvent(ExpenseTrackerUIEvent.DeleteButtonClicked)
                    }
                )
            }
        }
    }
}

@Composable
fun AmountTextField(
    textValue: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            if(isAmountValidInput(newValue)) {
                onTextChange(newValue)
            }
        },
        label = { Text(text = stringResource(id = R.string.amount)) } ,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
    )
}

fun isAmountValidInput(input: String): Boolean {
    if (input.isEmpty()) return true

    val regex = "^\\d*\\.?\\d{0,2}\$".toRegex()
    return input.matches(regex)
}

@Composable
fun DescriptionTextField(
    textValue: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = textValue,
        onValueChange = onTextChange,
        label = { Text(text = stringResource(id = R.string.description)) } ,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun CategoryDropdown(
    label: String,
    selectedCategory: ExpenseCategory,
    isDropdownExpanded: Boolean,
    onCategorySelected: (ExpenseCategory) -> Unit,
    onToggleDropdown: () -> Unit
) {
    Column {
        OutlinedTextField(
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            value = getCategoryDisplayName(category = selectedCategory),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { onToggleDropdown() }
                )
            },
        )

        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = onToggleDropdown,
            modifier = Modifier.fillMaxWidth()
        ) {
            ExpenseCategory.entries.forEach { category ->
                val textCategory = getCategoryDisplayName(category = category)
                DropdownMenuItem(
                    onClick = {
                        onCategorySelected(category)
                        onToggleDropdown()
                    },
                    text = { Text(textCategory)}
                )
            }
        }
    }
}

@Composable
fun ExpenseDonutChart(
    pieChartData: PieChartData,
    onCategorySelected: (ExpenseCategory) -> Unit
) {
    val pieChartConfig = PieChartConfig(
        labelVisible = true,
        strokeWidth = 100f,
        labelColor = Color.Black,
        activeSliceAlpha = .9f,
        isEllipsizeEnabled = true,
        labelTypeface = Typeface.defaultFromStyle(Typeface.BOLD),
        isAnimationEnable = true,
        chartPadding = 45,
        labelFontSize = 22.sp,
        isSumVisible = true,
        sumUnit = "zł"
    )

    DonutPieChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        pieChartData = pieChartData,
        pieChartConfig = pieChartConfig,
        onSliceClick = { slice ->
            val selectedCategory = getCategoryFromLabel(slice.label)
            onCategorySelected(selectedCategory)
        })
}


private fun getCategoryFromLabel(label: String): ExpenseCategory {
    return when (label) {
        getCategoryDisplayName(ExpenseCategory.HOME_AND_BILLS) -> ExpenseCategory.HOME_AND_BILLS
        getCategoryDisplayName(ExpenseCategory.FOOD) -> ExpenseCategory.FOOD
        getCategoryDisplayName(ExpenseCategory.HEALTH_AND_BEAUTY) -> ExpenseCategory.HEALTH_AND_BEAUTY
        getCategoryDisplayName(ExpenseCategory.ENTERTAINMENT_AND_TRAVEL) -> ExpenseCategory.ENTERTAINMENT_AND_TRAVEL
        getCategoryDisplayName(ExpenseCategory.CLOTHES_AND_ACCESSORIES) -> ExpenseCategory.CLOTHES_AND_ACCESSORIES
        getCategoryDisplayName(ExpenseCategory.EDUCATION) -> ExpenseCategory.EDUCATION
        getCategoryDisplayName(ExpenseCategory.TRANSPORT) -> ExpenseCategory.TRANSPORT
        getCategoryDisplayName(ExpenseCategory.OTHER) -> ExpenseCategory.OTHER
        else -> ExpenseCategory.OTHER
    }
}

@Composable
fun CurrentMonthRow(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(
            onClick = { onMonthChange(currentMonth.minusMonths(1)) },
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }
        val monthName =
            DateFormatter.getMonthNameInPolish(LocalContext.current, currentMonth.month.value)
        MonthNameComponent(value = monthName)
        IconButton(
            onClick = { onMonthChange(currentMonth.plusMonths(1)) }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun MonthNameComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .heightIn(),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        ),
        color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center
    )
}
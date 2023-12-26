package com.blach.unilife.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.blach.unilife.R
import com.blach.unilife.model.data.calendar.CalendarEvent
import com.blach.unilife.model.data.calendar.CalendarEventType
import com.blach.unilife.model.data.calendar.SimpleCalendarEvent
import com.blach.unilife.ui.utils.DateFormatter.getMonthNameInPolish
import com.blach.unilife.ui.utils.DateFormatter.getWeekDayNameInPolish
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

@Composable
fun CalendarDay(
    modifier: Modifier,
    day: Int,
    onDayClicked: (LocalDate) -> Unit,
    currentMonth: YearMonth,
    simpleEvents: List<SimpleCalendarEvent>

) {
    val dayDate = currentMonth.atDay(day)

    Box(
        modifier = modifier
            .height(70.dp)
            .border(
                2.dp,
                Color.Gray,
                RoundedCornerShape(4.dp)
            )
            .clickable(onClick = { onDayClicked(dayDate) })
            .padding(4.dp)
    ) {
        Column {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(10.dp))
            simpleEvents.forEach { event ->
                val eventColor = when (event.type) {
                    CalendarEventType.ACADEMIC -> colorResource(id = R.color.event_academic)
                    CalendarEventType.PERSONAL -> colorResource(id = R.color.event_personal)
                }

                if(event.day == dayDate.dayOfWeek.toString() || dayDate.equals(event.date)) {
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth()
                            .background(color = eventColor)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

    }
}

@Composable
fun CalendarWeek(
    week: Int,
    firstDayOffset: Int,
    daysInMonth: Int,
    onDayClicked: (LocalDate) -> Unit,
    currentMonth: YearMonth,
    simpleEvents: List<SimpleCalendarEvent>

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        for(dayOfWeek in 1..7) {
            val dayOfMonth = week * 7 + dayOfWeek - firstDayOffset
            if(dayOfMonth in 1..daysInMonth) {
                CalendarDay(
                    modifier = Modifier.weight(1f),
                    day = dayOfMonth,
                    onDayClicked = onDayClicked,
                    currentMonth = currentMonth,
                    simpleEvents = simpleEvents
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun CalendarMonth(
    currentMonth: YearMonth,
    onDayClicked: (LocalDate) -> Unit,
    simpleEvents: List<SimpleCalendarEvent>
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOffset = currentMonth.atDay(1).dayOfWeek.value % 7 - 1
    Column {
        for(week in 0 until ceil((daysInMonth + firstDayOffset) / 7f).toInt()) {
            CalendarWeek(
                week = week,
                firstDayOffset = firstDayOffset,
                daysInMonth = daysInMonth,
                currentMonth = currentMonth,
                onDayClicked = onDayClicked,
                simpleEvents = simpleEvents
            )
        }
    }
}

@Composable
fun DisplayAndChangeMonthRow(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(
            onClick = { onMonthChange(currentMonth.minusMonths(1)) },
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }
        val monthName = getMonthNameInPolish(LocalContext.current, currentMonth.month.value)
        MonthNameTextComponent(value = monthName)
        IconButton(
            onClick = { onMonthChange(currentMonth.plusMonths(1)) }
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

@Composable
fun MonthNameTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        ),
        color = colorResource(id = R.color.black),
        textAlign = TextAlign.Center
    )
}

@Composable
fun DaysOfWeekRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_monday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_tuesday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_wednesday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_thursday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_friday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_saturday), modifier = Modifier.weight(1f))
        WeekDayNameComponent(weekDayName = stringResource(id = R.string.weekday_sunday), modifier = Modifier.weight(1f))
    }
}

@Composable
fun WeekDayNameComponent(
    weekDayName: String,
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .padding(4.dp)
    ) {
        Text(
            text = weekDayName,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun AddNewEventButton(
    onButtonClicked: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .size(75.dp),
        onClick = {
            onButtonClicked.invoke()
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        shape = RoundedCornerShape(45.dp),
        containerColor = colorResource(id = R.color.purple_500)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)

    }
}

@Composable
fun DayEventsDialog(
    selectedDay: LocalDate,
    onDismiss: () -> Unit,
    events: List<CalendarEvent>,
    onDeleteEvent: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val monthName = getMonthNameInPolish(LocalContext.current, selectedDay.monthValue)
                val dayName = getWeekDayNameInPolish(LocalContext.current, selectedDay.dayOfWeek.value)

                Spacer(modifier = Modifier.padding(10.dp))
                HeadingTextComponent(value = dayName)
                Text(text = "${selectedDay.dayOfMonth} $monthName")

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(events) { event ->
                        EventItem(
                            event = event,
                            onDelete = { onDeleteEvent(event.id) },
                            onClick = { onClick(event.id) }
                            )
                    }
                }
            }
        }
    }
}

@Composable
fun EventItem(
    event: CalendarEvent,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val backgroundColor = when (event) {
        is CalendarEvent.Academic -> colorResource(id = R.color.event_academic)
        is CalendarEvent.Personal -> colorResource(id = R.color.event_personal)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "${event.startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - ${event.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    Log.d("EventItem", "Deleting event with ID: ${event.id}")
                    onDelete() }) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                }
            }

            event.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (event is CalendarEvent.Academic) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.professor, event.professor),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${stringResource(R.string.building, event.building)} - ${stringResource(R.string.room, event.roomNumber)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTextField(
    labelValue: String,
    textValue: String,
    onTextChange: (String) -> Unit,
) {

    OutlinedTextField(
        label = { Text(text = labelValue) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.primary),
            focusedLabelColor = colorResource(id = R.color.primary),
            cursorColor = colorResource(id = R.color.primary),
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        onValueChange = onTextChange,
        value = textValue,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDescriptionTextField(
    labelValue: String,
    textValue: String,
    onTextChange: (String) -> Unit,
) {

    OutlinedTextField(
        label = { Text(text = labelValue) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.primary),
            focusedLabelColor = colorResource(id = R.color.primary),
            cursorColor = colorResource(id = R.color.primary),
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 5,
        minLines = 4,
        onValueChange = onTextChange,
        value = textValue
    )
}

@Composable
fun MyTimePicker(
    isDialogOpen: Boolean,
    onDialogDismiss: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    initialTime: LocalTime
) {
    if(isDialogOpen) {
        val context = LocalContext.current
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hour, minute ->
                onTimeSelected(LocalTime.of(hour, minute))
            },
            initialTime.hour,
            initialTime.minute,
            true
        )
        timePickerDialog.setOnDismissListener { onDialogDismiss() }
        timePickerDialog.show()
    }
}

@Composable
fun MyDatePicker(
    isDialogOpen: Boolean,
    onDialogDismiss: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    initialDate: LocalDate
) {
    val context = LocalContext.current
    if (isDialogOpen) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        )

        datePickerDialog.setOnDismissListener { onDialogDismiss() }
        datePickerDialog.show()
    }
}

@Composable
fun WeekDayDropdown(
    label: String,
    options: List<String>,
    selectedDay: String,
    isDropdownExpanded: Boolean,
    onDaySelected: (String) -> Unit,
    onToggleDropdown: () -> Unit
) {
    Column {
        OutlinedTextField(
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            value = selectedDay,
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
            options.forEach { day ->
                DropdownMenuItem(
                    onClick = {
                        onDaySelected(day)
                        onToggleDropdown()
                    },
                    text = { Text(day)}
                )
            }
        }
    }
}

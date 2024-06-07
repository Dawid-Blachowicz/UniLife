package com.blach.unilife.view.data.calendar

import java.time.LocalDate
import java.time.LocalTime

data class CalendarEventUIState(
    val title: String = "",
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val description: String = "",
    val day: String = "",
    val professor: String = "",
    val roomNumber: String = "",
    val building: String = "",
    val date: LocalDate = LocalDate.now(),
    val isAcademic: Boolean = false,
    val isStartTimePickerDialogOpen: Boolean = false,
    val isEndTimePickerDialogOpen: Boolean = false,
    val isDatePickerDialogOpen: Boolean = false,
    val isWeekDayDropDownExpanded: Boolean = false,
    val isDataLoaded: Boolean = false
)

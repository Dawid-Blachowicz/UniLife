package com.blach.unilife.ui.data.calendar

import java.time.LocalDate
import java.time.YearMonth

sealed class CalendarUIEvent{
    data class DaySelected(val day: LocalDate): CalendarUIEvent()
    data class CurrentMonthChanged(val month: YearMonth): CalendarUIEvent()
    data class EventDeleted(val eventId: String): CalendarUIEvent()

    data object DayDialogDismissed: CalendarUIEvent()
}

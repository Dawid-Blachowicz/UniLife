package com.blach.unilife.view.data.calendar

import com.blach.unilife.model.data.calendar.CalendarEvent
import com.blach.unilife.model.data.calendar.SimpleCalendarEvent
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUIState(
    val selectedDay: LocalDate? = null,
    val currentMonth: YearMonth = YearMonth.now(),
    val eventsForSelectedDay: List<CalendarEvent> = emptyList(),
    val simpleEventsForSelectedMonth: List<SimpleCalendarEvent> = emptyList(),
)

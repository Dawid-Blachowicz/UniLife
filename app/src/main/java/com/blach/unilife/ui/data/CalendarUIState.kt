package com.blach.unilife.ui.data

import com.blach.unilife.model.data.CalendarEvent
import com.blach.unilife.model.data.SimpleCalendarEvent
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUIState(
    val selectedDay: LocalDate? = null,
    val currentMonth: YearMonth = YearMonth.now(),
    val eventsForSelectedDay: List<CalendarEvent> = emptyList(),
    val simpleEventsForSelectedMonth: List<SimpleCalendarEvent> = emptyList()
)

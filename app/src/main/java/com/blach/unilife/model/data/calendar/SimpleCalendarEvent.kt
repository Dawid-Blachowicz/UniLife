package com.blach.unilife.model.data.calendar

import com.blach.unilife.model.data.calendar.CalendarEventType
import java.time.LocalDate

data class SimpleCalendarEvent(
    val type: CalendarEventType,
    val day: String?,
    val date: LocalDate?
)

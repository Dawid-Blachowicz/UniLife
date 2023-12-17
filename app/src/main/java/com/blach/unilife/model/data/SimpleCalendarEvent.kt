package com.blach.unilife.model.data

import java.time.LocalDate

data class SimpleCalendarEvent(
    val type: CalendarEventType,
    val day: String?,
    val date: LocalDate?
)

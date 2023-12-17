package com.blach.unilife.model.data.dto

data class CalendarEventDTO(
    val id: String = "",
    val title: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val description: String? = null,
    val type: String = "",
    val professor: String? = null,
    val roomNumber: String? = null,
    val building: String? = null,
    val day: String? = null,
    val date: String? = null
)

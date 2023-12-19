package com.blach.unilife.model.data.notes

import java.time.LocalDate

data class Note(
    val id: String,
    val title: String? = "",
    val content: String? = "",
    val lastEditDate: LocalDate
)

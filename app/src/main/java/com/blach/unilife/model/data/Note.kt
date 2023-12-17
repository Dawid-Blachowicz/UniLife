package com.blach.unilife.model.data

import java.time.LocalDate

data class Note(
    val id: String,
    val title: String? = null,
    val content: String? = null,
    val lastEditDate: LocalDate
)

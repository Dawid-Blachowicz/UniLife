package com.blach.unilife.model.data.todo

import java.time.LocalDate

data class TodoTask(
    val id: String,
    val content: String,
    val isChecked: Boolean,
    val creationDate: LocalDate
)

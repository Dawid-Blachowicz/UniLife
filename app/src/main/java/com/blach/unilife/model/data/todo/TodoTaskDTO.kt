package com.blach.unilife.model.data.todo

data class TodoTaskDTO(
    val content: String = "",
    val checked: Boolean = false,
    val creationDate: String = ""
)

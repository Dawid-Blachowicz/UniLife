package com.blach.unilife.view.data.todo

import com.blach.unilife.model.data.todo.TodoTask

data class TodoUIState(
    val todoTasks: List<TodoTask> = emptyList(),
    val doneTasks: List<TodoTask> = emptyList(),
    val content: String = "",
    val isChecked: Boolean = false,
    val openTaskDialog: Boolean = false,
    val selectedTabIndex: Int = 0
)

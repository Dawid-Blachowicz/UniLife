package com.blach.unilife.ui.data.todo

import com.blach.unilife.model.data.todo.TodoTask

sealed class TodoUIEvent {
    data class IsCheckedChanged(val taskId: String, val isChecked: Boolean): TodoUIEvent()
    data class OpenTaskDialogChanged( val openTaskDialog: Boolean): TodoUIEvent()
    data class LeadTask(val task: TodoTask): TodoUIEvent()
    data class ContentChanged(val content: String): TodoUIEvent()
    data class SelectedTabIndexChanged(val selectedTabIndex: Int): TodoUIEvent()

    data object SaveButtonClicked: TodoUIEvent()
    data object DeleteButtonClicked: TodoUIEvent()
}

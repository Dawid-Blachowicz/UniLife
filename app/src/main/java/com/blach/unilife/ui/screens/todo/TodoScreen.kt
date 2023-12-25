package com.blach.unilife.ui.screens.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.blach.unilife.ui.components.TaskItem
import com.blach.unilife.ui.data.todo.TodoUIEvent
import com.blach.unilife.viewmodels.todo.TodoViewModel

@Composable
fun TodoScreen(
    viewModel: TodoViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val uiState by viewModel.uiState.collectAsState()

        LazyColumn {
            items(uiState.todoTasks) {taskItem ->
                TaskItem(
                    task = taskItem,
                    textColor = Color.Black,
                    onClick = {
                        viewModel.onEvent(TodoUIEvent.LeadTask(taskItem))
                        viewModel.onEvent(TodoUIEvent.OpenTaskDialogChanged(true))
                    },
                    onCheck = {
                        viewModel.onEvent(TodoUIEvent.IsCheckedChanged(taskItem.id, it))
                    },
                    onDelete = {
                        viewModel.onEvent(TodoUIEvent.LeadTask(taskItem))
                        viewModel.onEvent(TodoUIEvent.DeleteButtonClicked)
                    }
                )
            }
        }
    }
}
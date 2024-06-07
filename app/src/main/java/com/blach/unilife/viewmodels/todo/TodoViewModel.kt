package com.blach.unilife.viewmodels.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blach.unilife.model.data.todo.TodoTask
import com.blach.unilife.model.repository.TodoRepository
import com.blach.unilife.view.data.todo.TodoUIEvent
import com.blach.unilife.view.data.todo.TodoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(TodoUIState())
    val uiState = _uiState.asStateFlow()

    private val tasks: StateFlow<List<TodoTask>> = repository.tasksFlow

    private var currentTaskId: String? = null

    init {
        viewModelScope.launch {
            repository.deleteDeprecatedTasks()
            repository.getTodoTasksForUser()
            tasks.collect {
                getTasksForLoggedUser(it)
            }
        }
    }

    fun onEvent(event: TodoUIEvent) {
        when(event) {
            is TodoUIEvent.IsCheckedChanged -> {
                val taskToUpdate = tasks.value.find { it.id == event.taskId }
                taskToUpdate?.let { task ->
                    currentTaskId = task.id
                    _uiState.value = _uiState.value.copy(
                        content = task.content,
                        isChecked = event.isChecked
                    )
                }

                updateTask()
                refreshData()
            }
            is TodoUIEvent.LeadTask -> {
                currentTaskId = event.task.id
                _uiState.value = _uiState.value.copy(
                    content = event.task.content,
                    isChecked =  event.task.isChecked,
                )
            }
            is TodoUIEvent.OpenTaskDialogChanged -> {
                _uiState.value = _uiState.value.copy(
                    openTaskDialog = event.openTaskDialog
                )
            }
            is TodoUIEvent.ContentChanged -> {
                _uiState.value = _uiState.value.copy(
                    content = event.content
                )
            }
            is TodoUIEvent.SelectedTabIndexChanged -> {
                _uiState.value = _uiState.value.copy(
                    selectedTabIndex = event.selectedTabIndex
                )
            }
            TodoUIEvent.SaveButtonClicked -> {
                if(currentTaskId == null) {
                    saveTodoTask()
                } else {
                    updateTask()
                }
                resetTodoState()
            }
            TodoUIEvent.DeleteButtonClicked -> {
                deleteTask()
            }

        }
    }

    private fun getTasksForLoggedUser(tasks: List<TodoTask>) {
        val todoTasks = tasks
            .filter { task -> !task.isChecked }

        val doneTasks = tasks
            .filter { task -> task.isChecked }

        _uiState.value = _uiState.value.copy(
            todoTasks = todoTasks,
            doneTasks = doneTasks
        )
    }

    private fun saveTodoTask() {
        val task = TodoTask(
            id = "",
            content = uiState.value.content,
            isChecked = false,
            creationDate = LocalDate.now()
        )
        repository.saveTaskForUser(task)
    }

    private fun updateTask() {
        val task = TodoTask(
            id = currentTaskId!!,
            content = uiState.value.content,
            isChecked = uiState.value.isChecked,
            creationDate = LocalDate.now()
        )
        repository.updateTaskForUser(task)
    }

    private fun deleteTask() {
        repository.deleteTaskForUser(currentTaskId!!)
    }

    private fun resetTodoState() {
        _uiState.value = TodoUIState()

    }

    private fun refreshData() {
        viewModelScope.launch {
            repository.getTodoTasksForUser()
            tasks.collect {
                getTasksForLoggedUser(it)
            }
        }
    }
}
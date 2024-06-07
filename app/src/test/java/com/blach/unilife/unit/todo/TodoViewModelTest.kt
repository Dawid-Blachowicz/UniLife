package com.blach.unilife.unit.todo

import com.blach.unilife.model.data.todo.TodoTask
import com.blach.unilife.model.repository.TodoRepository
import com.blach.unilife.view.data.todo.TodoUIEvent
import com.blach.unilife.viewmodels.todo.TodoViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class TodoViewModelTest {

    private lateinit var viewModel: TodoViewModel
    private val repository: TodoRepository = mockk(relaxed = true)
    private val tasksFlow = MutableStateFlow<List<TodoTask>>(emptyList())
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { repository.tasksFlow } returns tasksFlow
        viewModel = TodoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `on init, tasks are loaded`() = runTest {
        coVerify { repository.deleteDeprecatedTasks() }
        coVerify { repository.getTodoTasksForUser() }
    }

    @Test
    fun `when isCheckedChanged, task is updated`() = runTest {
        val taskId = "testTaskId"
        val testTask = TodoTask(id = taskId, content = "Test Content", isChecked = false, LocalDate.now())
        tasksFlow.value = listOf(testTask)

        viewModel.onEvent(TodoUIEvent.IsCheckedChanged(taskId, true))

        coVerify { repository.updateTaskForUser(match { it.id == taskId && it.isChecked }) }
    }

    @Test
    fun `on saveButtonClicked with new task, save task`() = runTest {
        viewModel.onEvent(TodoUIEvent.SaveButtonClicked)

        coVerify { repository.saveTaskForUser(any()) }
    }

    @Test
    fun `on deleteButtonClicked, delete task`() = runTest {
        val taskId = "testTaskId"
        viewModel.onEvent(TodoUIEvent.LeadTask(TodoTask(id = taskId, content = "Test", isChecked = false, LocalDate.now())))

        viewModel.onEvent(TodoUIEvent.DeleteButtonClicked)

        coVerify { repository.deleteTaskForUser(taskId) }
    }

    @Test
    fun `on leadTask event, update UI state`() = runTest {
        val testTask = TodoTask(id = "testId", content = "Test Content", isChecked = false, LocalDate.now())

        viewModel.onEvent(TodoUIEvent.LeadTask(testTask))

        assertEquals(viewModel.uiState.value.content, testTask.content)
    }
}

package com.blach.unilife.unit.notes
import com.blach.unilife.model.data.notes.Note
import com.blach.unilife.model.repository.NotesRepository
import com.blach.unilife.view.data.notes.NotesUIEvent
import com.blach.unilife.viewmodels.notes.NotesViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class NotesViewModelTest {

    private lateinit var viewModel: NotesViewModel
    private val repository: NotesRepository = mockk(relaxed = true)
    private val notesFlow = MutableStateFlow<List<Note>>(emptyList())
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { repository.notesFlow } returns notesFlow
        viewModel = NotesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `when init, load notes for user`() {
        coVerify { repository.getNotesForUser() }
    }

    @Test
    fun `when TitleChanged event, update title in uiState`() {
        val testTitle = "Test Title"
        viewModel.onEvent(NotesUIEvent.TitleChanged(testTitle))
        assertEquals(testTitle, viewModel.uiState.value.title)
    }

    @Test
    fun `when ContentChanged event, update content in uiState`() {
        val testContent = "Test Content"
        viewModel.onEvent(NotesUIEvent.ContentChanged(testContent))
        assertEquals(testContent, viewModel.uiState.value.content)
    }

    @Test
    fun `when LastEditDateChanged event, update lastEditDate in uiState`() {
        val testDate = LocalDate.now()
        viewModel.onEvent(NotesUIEvent.LastEditDateChanged(testDate))
        assertEquals(testDate, viewModel.uiState.value.lastEditDate)
    }

    @Test
    fun `when LeadNote event, set current node id and update uiState`() {
        val testNote = Note("id", "title", "content", LocalDate.now())
        viewModel.onEvent(NotesUIEvent.LeadNote(testNote))
        assertEquals(testNote.id, viewModel.currentNodeId)
        assertEquals(testNote.title, viewModel.uiState.value.title)
        assertEquals(testNote.content, viewModel.uiState.value.content)
    }

    @Test
    fun `when OpenDeleteDialogChanged event, update openDeleteDialog in uiState`() {
        viewModel.onEvent(NotesUIEvent.OpenDeleteDialogChanged(true))
        assertTrue(viewModel.uiState.value.openDeleteDialog)
    }

    @Test
    fun `when SaveButtonClicked with new note, call saveNote`() {
        viewModel.onEvent(NotesUIEvent.TitleChanged("New Note"))
        viewModel.onEvent(NotesUIEvent.SaveButtonClicked)
        coVerify { repository.saveNoteForUser(any()) }
    }

    @Test
    fun `when SaveButtonClicked with existing note, call updateNote`() {
        viewModel.currentNodeId = "testId"
        viewModel.onEvent(NotesUIEvent.SaveButtonClicked)
        coVerify { repository.updateNoteForUser(any()) }
    }

    @Test
    fun `when ConfirmDeleteButtonClicked, call deleteNote`() {
        viewModel.currentNodeId = "testId"
        viewModel.onEvent(NotesUIEvent.ConfirmDeleteButtonClicked)
        coVerify { repository.deleteNoteForUser("testId") }
    }

    @Test
    fun `when notes flow emits, update UI state with sorted notes`() {
        val testNote1 = Note(id = "1", title = "Note 1", content = "Content 1", lastEditDate = LocalDate.now().minusDays(1))
        val testNote2 = Note(id = "2", title = "Note 2", content = "Content 2", lastEditDate = LocalDate.now())
        val testNotes = listOf(testNote1, testNote2)

        notesFlow.value = testNotes

        runBlocking {
            delay(100)
        }

        val sortedNotes = viewModel.uiState.value.notesForLoggedUser
        assertTrue(sortedNotes.isNotEmpty())
        assertEquals(2, sortedNotes.size)
        assertEquals("Note 2", sortedNotes[0].title)
        assertEquals("Note 1", sortedNotes[1].title)
    }

}

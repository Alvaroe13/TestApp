package com.ai.notelist

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.ResultWrapperError
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNotesByQuery
import com.ai.notelist.dispatcher.DispatcherProviderTestImpl
import com.ai.notelist.repository.NotesRepositoryTestImpl
import com.ai.notelist.utils.assertErrorIsFalse
import com.ai.notelist.utils.assertErrorIsTrue
import com.ai.notelist.utils.assertIsNotLoading
import com.ai.notelist.utils.assertNoteForDeletionNotSelected
import com.ai.notelist.utils.assertNoteForDeletionSelected
import com.ai.notelist.utils.assertNotesAreEmpty
import com.ai.notelist.utils.assertShowDeletionFalse
import com.ai.notelist.utils.assertShowDeletionTrue
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Inspired on this Medium article
 * https://betterprogramming.pub/how-to-make-unit-tests-for-viewmodel-easier-to-write-and-maintain-a7efd74cc4db
 */

@OptIn(ExperimentalCoroutinesApi::class)
class NoteListViewModelTest {

    private lateinit var viewModel: NoteListViewModel

    private lateinit var dispatcherProvider: DispatcherProviderTestImpl
    private lateinit var notesRepository: NotesRepository
    private lateinit var getNotesByQuery: GetNotesByQuery

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        dispatcherProvider = DispatcherProviderTestImpl()
        getNotesByQuery = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `given fetching all notes success the error value should be set to false`() = runTest {

        //given
        notesRepository = mockk()
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Success(listOf()) }

        //when
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)
        runCurrent()

        //then
        viewModel.apply {
            assertIsNotLoading()
            assertNotesAreEmpty()
            assertErrorIsFalse()
        }

    }

    @Test
    fun `given fetching all notes fails the error value should be set to true with mockk`() = runTest {

        //given
        notesRepository = mockk()
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Failure(ResultWrapperError.Unknown) }

        //when
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)
        runCurrent()

        //then
        viewModel.apply {
            assertIsNotLoading()
            assertNotesAreEmpty()
            assertErrorIsTrue()
        }

    }

    @Test
    fun `given fetching all notes fails the error value should be set to true with fake repo`() = runTest {

        //given
        notesRepository = NotesRepositoryTestImpl()

        //when
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)
        runCurrent()

        //then
        viewModel.apply {
            assertIsNotLoading()
            assertNotesAreEmpty()
            assertErrorIsTrue()
        }
    }

    @Test
    fun `given fetching all notes first fails then refresh page and returns success response sets error value to false`() = runTest {
        //given
        notesRepository = mockk()
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Failure(ResultWrapperError.Unknown) }

        //when
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)
        runCurrent()

        //then
        viewModel.apply {
            assertIsNotLoading()
            assertNotesAreEmpty()
            assertErrorIsTrue()
        }

        //when
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Success(listOf()) }
        viewModel.setAction(NoteListScreenActions.LoadNotes)
        runCurrent()

        //then
        viewModel.apply {
            assertIsNotLoading()
            assertNotesAreEmpty()
            assertErrorIsFalse()
        }
    }

    @Test
    fun `given note pressed for deletion shows dialog and on tap hides it`() = runTest {
        //given
        notesRepository = mockk()
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Success(listOf()) }
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)

        //when
        viewModel.setAction(NoteListScreenActions.OnNoteLongPressed(NoteEntity()))
        runCurrent()

        //then.
        viewModel.apply {
            assertShowDeletionTrue()
        }

        //when
        viewModel.setAction(NoteListScreenActions.OnNoteDeletionCancelled)
        runCurrent()

        //then
        viewModel.apply {
            assertShowDeletionFalse()
        }
    }

    @Test
    fun `given selected note for deletion then is no longer selected`() = runTest {
        //given
        notesRepository = mockk()
        coEvery { notesRepository.getAllNotes() } coAnswers { ResultWrapper.Success(listOf()) }
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)

        //when
        viewModel.setAction(NoteListScreenActions.OnNoteLongPressed(NoteEntity()))
        runCurrent()

        //then
        viewModel.apply {
            assertNoteForDeletionSelected()
        }

        //when
        viewModel.setAction(NoteListScreenActions.OnNoteDeletionCancelled)
        runCurrent()

        //then

        viewModel.apply {
            assertNoteForDeletionNotSelected()
        }
    }
}
package com.ai.notelist

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.ResultWrapperError
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNotesByQuery
import com.ai.notelist.dispatcher.DispatcherProviderTestImpl
import com.ai.notelist.repository.NotesRepositoryTestImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
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
        assertTrue(viewModel.screenState.value.notes.isEmpty())
        assertFalse(viewModel.screenState.value.error)
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
        assertTrue(viewModel.screenState.value.notes.isEmpty())
        assertTrue(viewModel.screenState.value.error)
    }

    @Test
    fun `given fetching all notes fails the error value should be set to true with fake repo`() = runTest {

        //given
        notesRepository = NotesRepositoryTestImpl()

        //when
        viewModel = NoteListViewModel(dispatcherProvider, notesRepository, getNotesByQuery)
        runCurrent()

        //then
        assertTrue(viewModel.screenState.value.notes.isEmpty())
        assertTrue(viewModel.screenState.value.error)
    }

}
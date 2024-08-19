package com.ai.notedetails

import androidx.lifecycle.SavedStateHandle
import com.ai.common.navigation.ArgumentKeyConstants.NOTE_ID_KEY
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNoteByIdUseCase
import com.ai.common_domain.usecase.GetRelatedNotes
import com.ai.notedetails.dispatcher.DispatcherProviderTestImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteDetailsViewModelTest {


    private lateinit var viewModel: NoteDetailsViewModel

    private lateinit var notesRepository: NotesRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var getRelatedNotes: GetRelatedNotes
    private lateinit var getNoteByIdUseCase: GetNoteByIdUseCase
    private lateinit var savedStateHandle: SavedStateHandle


    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher) //needed for test coroutines/suspend functions
        notesRepository = mockk()
        dispatcherProvider = DispatcherProviderTestImpl()
        getRelatedNotes = mockk()
        getNoteByIdUseCase = mockk()
        savedStateHandle = SavedStateHandle()
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `given saving new note set state accordingly`() = runTest {
        //when
        viewModel = NoteDetailsViewModel(dispatcherProvider, getNoteByIdUseCase, getRelatedNotes, notesRepository, savedStateHandle)
        runCurrent()

        //then
        assertFalse(viewModel.screenState.value.isLoading)
        assertFalse(viewModel.screenState.value.error)
        assertFalse(viewModel.screenState.value.showOptions)

        assertTrue(viewModel.screenState.value.note.id == null)
        assertTrue(viewModel.screenState.value.noteState == NoteState.INSERT)
    }

    @Test
    fun `given editing a note retrieve note when landing set error to false `() = runTest {
        //given
        val note = NoteEntity(id = 500)
        savedStateHandle[NOTE_ID_KEY] = note.id.toString()
        coEvery { getNoteByIdUseCase(any()) } returns ResultWrapper.Success(note)
        coEvery { getRelatedNotes(any()) } returns ResultWrapper.Success(listOf())

        //when
        viewModel = NoteDetailsViewModel(dispatcherProvider, getNoteByIdUseCase, getRelatedNotes, notesRepository, savedStateHandle)
        runCurrent()

        //then
        assertFalse(viewModel.screenState.value.isLoading)
        assertFalse(viewModel.screenState.value.error)
        assertFalse(viewModel.screenState.value.showOptions)

        assertTrue(viewModel.screenState.value.note.id == 500)
        assertTrue(viewModel.screenState.value.noteState == NoteState.EDIT)
    }

    @Test
    fun `given existing note type tapped set showOptions value to true`() = runTest {
        //given
        val note = NoteEntity(id = 500)
        savedStateHandle[NOTE_ID_KEY] = note.id.toString()
        coEvery { getNoteByIdUseCase(any()) } returns ResultWrapper.Success(note)
        coEvery { getRelatedNotes(any()) } returns ResultWrapper.Success(listOf())

        //when
        viewModel = NoteDetailsViewModel(dispatcherProvider, getNoteByIdUseCase, getRelatedNotes, notesRepository, savedStateHandle)
        viewModel.setAction(NoteDetailsScreenActions.OnTypeChanged(true))
        runCurrent()

        //then
        assertFalse(viewModel.screenState.value.isLoading)
        assertFalse(viewModel.screenState.value.error)

        assertTrue(viewModel.screenState.value.showOptions)
        assertTrue(viewModel.screenState.value.note.id == 500)
        assertTrue(viewModel.screenState.value.noteState == NoteState.EDIT)
    }

}
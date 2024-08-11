package com.ai.notedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.NoteType
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNoteByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject
constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getNoteByIdUsaCase: GetNoteByIdUseCase,
    private val repository: NotesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteDetailsScreenState, NoteDetailsScreenActions, NoteDetailsScreenEffect > () {

    init {
        savedStateHandle.get<String>(NOTE_ID_KEY)?.let { noteId ->
            viewModelScope.launch(dispatcherProvider.io()) {
                val result = getNoteByIdUsaCase(noteId)
                if (result is ResultWrapper.Success) {
                    setScreenState {
                        copy(
                            note = result.data,
                            noteState = NoteState.EDIT
                        )
                    }
                } else {
                    setScreenState {
                        copy(error = true)
                    }
                }
            }
        }
    }

    override fun createInitialScreenSate(): NoteDetailsScreenState  = NoteDetailsScreenState()

    override suspend fun handleActions(action: NoteDetailsScreenActions) {
        when(action) {
            is NoteDetailsScreenActions.GetRelatedNoted -> {}
            is NoteDetailsScreenActions.UpdateNote -> {}
            is NoteDetailsScreenActions.OnNameChanged -> {
                setScreenState {
                    copy(note = currentScreenState.note.copy(name = action.name))
                }
            }
            is NoteDetailsScreenActions.OnDescriptionChanged -> {
                setScreenState {
                    copy(note = currentScreenState.note.copy(description = action.description))
                }
            }
            is NoteDetailsScreenActions.OnTypeChanged -> {}
            is NoteDetailsScreenActions.SaveNote -> {
                val note = currentScreenState.note
                if (currentScreenState.noteState == NoteState.INSERT) {
                    insertNote(note)
                } else {
                    updateNote(note)
                }
            }
        }
    }


    private suspend fun insertNote(note: NoteEntity) {
        viewModelScope.launch(dispatcherProvider.io()) {
            //here we could/should add an use case for validating the info contained in the entity before creating new note for a production app
            repository.insertNote(note)
            withContext(dispatcherProvider.ui()) {
                setEffect { NoteDetailsScreenEffect.GoBack }
            }
        }
    }

    private suspend fun updateNote(note: NoteEntity) {
        viewModelScope.launch(dispatcherProvider.io()) {
            //here we could/should add an use case for validating the info contained in the entity before creating new note for a production app
            repository.updateNote(note)
            withContext(dispatcherProvider.ui()) {
                setEffect { NoteDetailsScreenEffect.GoBack }
            }
        }
    }

    companion object {
        private const val NOTE_ID_KEY = "noteId"
    }
}

enum class NoteState {
    INSERT,
    EDIT
}


data class NoteDetailsScreenState(
    val isLoading: Boolean = false,
    val note: NoteEntity = NoteEntity(),
    val noteState: NoteState = NoteState.INSERT,
    val error: Boolean = false // this should be a class exposing the error with message but for this sample should be enough
): ScreenState

sealed class NoteDetailsScreenActions : Action {
    object SaveNote: NoteDetailsScreenActions()
    data class UpdateNote(val note: NoteEntity) : NoteDetailsScreenActions()
    object GetRelatedNoted : NoteDetailsScreenActions()
    data class OnNameChanged(val name: String) : NoteDetailsScreenActions()
    data class OnDescriptionChanged(val description: String) : NoteDetailsScreenActions()
    data class OnTypeChanged(val type: NoteType) : NoteDetailsScreenActions()
}

sealed class NoteDetailsScreenEffect : Effect {
    object GoBack: NoteDetailsScreenEffect()
}
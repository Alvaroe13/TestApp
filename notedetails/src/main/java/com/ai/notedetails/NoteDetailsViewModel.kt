package com.ai.notedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ai.common.navigation.ArgumentKeyConstants.NOTE_ID_KEY
import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.entities.NoteObject
import com.ai.common_domain.extentions.onError
import com.ai.common_domain.extentions.onSuccess
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.usecase.GetNoteByIdUseCase
import com.ai.common_domain.usecase.GetRelatedNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject
constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getNoteByIdUsaCase: GetNoteByIdUseCase,
    private val getRelatedNotes: GetRelatedNotes,
    private val repository: NotesRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteDetailsScreenState, NoteDetailsScreenActions, NoteDetailsScreenEffect > () {

    init {
        savedStateHandle.get<String>(NOTE_ID_KEY)?.let { noteId ->
            viewModelScope.launch(dispatcherProvider.ui()) {
                handleActions( NoteDetailsScreenActions.GetNoteAndRelated(noteId) )
            }
        }
    }

    override fun createInitialScreenSate(): NoteDetailsScreenState  = NoteDetailsScreenState()

    override suspend fun handleActions(action: NoteDetailsScreenActions) {
        when(action) {
            is NoteDetailsScreenActions.GetNoteAndRelated -> {
                getNoteByIdUsaCase(action.noteId)
                    .onSuccess { note ->
                        setScreenState {
                            copy(note = note, noteState = NoteState.EDIT)
                        }
                        viewModelScope.launch(dispatcherProvider.ui()) {
                            getRelatedNotes(note)
                                .onSuccess { relatedNotes->
                                    setScreenState {
                                        copy(relatedNotes = relatedNotes)
                                    }
                                }
                        }
                    }
                    .onError {
                        setScreenState {
                            copy(error = true)
                        }
                    }
            }
            is NoteDetailsScreenActions.UpdateNote -> {}
            is NoteDetailsScreenActions.OnTypeSelected -> {
                setScreenState {
                    copy(
                        note = currentScreenState.note.copy(type = action.noteObject)
                    )
                }
            }
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
            is NoteDetailsScreenActions.OnTypeChanged -> {
                setScreenState {
                    copy(showOptions = action.show)
                }
            }
            is NoteDetailsScreenActions.SaveNote -> {
                val note = currentScreenState.note
                if (currentScreenState.noteState == NoteState.INSERT) {
                    insertNote(note)
                } else {
                    updateNote(note)
                }
            }
            is NoteDetailsScreenActions.OnRelatedNoteSelected -> setEffect { NoteDetailsScreenEffect.OpenRelatedNote(action.note.id.toString()) }
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
}

enum class NoteState {
    INSERT,
    EDIT
}


data class NoteDetailsScreenState(
    val isLoading: Boolean = false,
    val note: NoteEntity = NoteEntity(),
    val noteState: NoteState = NoteState.INSERT,
    val relatedNotes: List<NoteEntity> = emptyList(),
    var showOptions: Boolean = false,
    val error: Boolean = false // this should be a class exposing the error with message but for this sample should be enough
): ScreenState

sealed class NoteDetailsScreenActions : Action {
    object SaveNote: NoteDetailsScreenActions()
    data class UpdateNote(val note: NoteEntity) : NoteDetailsScreenActions()
    data class GetNoteAndRelated(val noteId: String) : NoteDetailsScreenActions()
    data class OnNameChanged(val name: String) : NoteDetailsScreenActions()
    data class OnDescriptionChanged(val description: String) : NoteDetailsScreenActions()
    data class OnTypeChanged(val show: Boolean) : NoteDetailsScreenActions()
    data class OnTypeSelected(val noteObject: NoteObject) : NoteDetailsScreenActions()
    data class OnRelatedNoteSelected(val note: NoteEntity) : NoteDetailsScreenActions()
}

sealed class NoteDetailsScreenEffect : Effect {
    object GoBack: NoteDetailsScreenEffect()
    data class OpenRelatedNote(val noteId: String): NoteDetailsScreenEffect()
}
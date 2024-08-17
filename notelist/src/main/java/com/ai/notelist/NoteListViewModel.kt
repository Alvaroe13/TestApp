package com.ai.notelist

import androidx.lifecycle.viewModelScope
import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
import com.ai.common_domain.extentions.onError
import com.ai.common_domain.extentions.onSuccess
import com.ai.common_domain.usecase.GetNotesByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val notesRepository: NotesRepository,
    private val getNotesByQuery: GetNotesByQuery
) : BaseViewModel<NoteListScreenState, NoteListScreenActions, NoteListScreenEffects> (){


    override fun createInitialScreenSate(): NoteListScreenState = NoteListScreenState()

    init {
        loadNotes()
    }

    override suspend fun handleActions(action: NoteListScreenActions) {
       when(action) {
           is NoteListScreenActions.LoadNotes -> loadNotes()

           is NoteListScreenActions.OnNoteSelectedClick ->  setEffect {
               NoteListScreenEffects.NoteSelected(checkNotNull(action.note.id) )
           }

           is NoteListScreenActions.OnNewNoteClick -> setEffect { NoteListScreenEffects.CreateNewNoteClicked }

           is NoteListScreenActions.OnNoteLongPressed -> {
               setScreenState {
                    copy(
                        showDeleteOption = true,
                        noteForDeletion = action.note,
                        error = false
                    )
               }
           }

           is NoteListScreenActions.OnNoteDeletionConfirmed -> {
               setScreenState {
                   copy(showDeleteOption = false)
               }
               viewModelScope.launch(dispatcherProvider.io()) {
                   notesRepository.deleteNote(action.note)
                       .onSuccess {
                           setScreenState {
                               copy(
                                   noteForDeletion = null,
                                   error = false
                               )
                           }
                           loadNotes()
                       }
                       .onError {
                           // show error message
                       }
               }
           }

           is NoteListScreenActions.OnNoteDeletionCancelled -> {
               setScreenState {
                   copy(showDeleteOption = false, error = false)
               }
           }
           is NoteListScreenActions.OnSearchQueryChanged -> {
               setScreenState {
                   copy(searchQuery = action.query)
               }
               getNotesByQuery(action.query).onSuccess { noteList ->
                   setScreenState {
                       copy(notes = noteList)
                   }
               }
           }
       }
    }

    private fun loadNotes() {
        viewModelScope.launch(dispatcherProvider.io()) {

            notesRepository.getAllNotes()
                .onSuccess { notes ->
                    setScreenState {
                        copy(
                            notes = notes,
                            error = false
                        )
                    }
                }
                .onError {
                    setScreenState {
                        copy(error = true)
                    }
                }
        }
    }
}

data class NoteListScreenState(
    val isLoading: Boolean = false,
    val notes: List<NoteEntity> = emptyList(),
    val showDeleteOption: Boolean = false,
    val noteForDeletion: NoteEntity? = null,
    var searchQuery: String? = null,
    val error: Boolean = false // this should be a class but for this sample should be enough
): ScreenState

sealed class NoteListScreenEffects : Effect{
    object CreateNewNoteClicked : NoteListScreenEffects()
    data class NoteSelected(val noteId: Int) : NoteListScreenEffects()
}

sealed class NoteListScreenActions : Action {

    object LoadNotes: NoteListScreenActions()
    object OnNewNoteClick : NoteListScreenActions()
    data class OnNoteLongPressed(val note: NoteEntity) : NoteListScreenActions()
    data class OnNoteDeletionConfirmed(val note: NoteEntity) : NoteListScreenActions()
    object OnNoteDeletionCancelled : NoteListScreenActions()
    data class OnNoteSelectedClick(val note: NoteEntity) : NoteListScreenActions()
    data class OnSearchQueryChanged(val query: String) : NoteListScreenActions()
}
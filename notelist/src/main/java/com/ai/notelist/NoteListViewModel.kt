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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val notesRepository: NotesRepository
) : BaseViewModel<NoteListScreenState, NoteListScreenActions, NoteListScreenEffects> (){


    override fun createInitialScreenSate(): NoteListScreenState = NoteListScreenState()

    init {
        loadNotes()
    }

    override suspend fun handleActions(action: NoteListScreenActions) {
       when(action) {
           is NoteListScreenActions.LoadNotes -> loadNotes()

           is NoteListScreenActions.OnNoteSelectedClick ->  setEffect {
               NoteListScreenEffects.NoteSelected(action.note.id ?: throw IllegalArgumentException("this id cannot be null") )
           }

           is NoteListScreenActions.OnNewNoteClick -> setEffect { NoteListScreenEffects.CreateNewNoteClicked }
           else -> {}
       }
    }

    private fun loadNotes() {
        viewModelScope.launch(dispatcherProvider.io()) {

            notesRepository.getAllNotes()
                .onSuccess { notes ->
                    setScreenState {
                        copy(notes = notes)
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
    val error: Boolean = false // this should be a class but for this sample should be enough
): ScreenState

sealed class NoteListScreenEffects : Effect{
    object CreateNewNoteClicked : NoteListScreenEffects()
    data class NoteSelected(val noteId: Int) : NoteListScreenEffects()
}

sealed class NoteListScreenActions : Action {

    object LoadNotes: NoteListScreenActions()
    object OnNewNoteClick : NoteListScreenActions()
    data class OnNoteSelectedClick(val note: NoteEntity) : NoteListScreenActions()
}
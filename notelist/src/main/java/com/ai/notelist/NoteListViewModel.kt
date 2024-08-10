package com.ai.notelist

import androidx.lifecycle.viewModelScope
import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.respository.NotesRepository
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
        viewModelScope.launch(dispatcherProvider.io()) {
            val notes = notesRepository.getAllNotes()
            if (notes is ResultWrapper.Success) { // Instead of if/else block in every screen we could create an extension function delivering the result with a more sophisticated logic
                println("NoteListViewModel if")
                setScreenState {
                    copy(notes = notes.data)
                }
            }
            if (notes is ResultWrapper.Failure) {
                println("NoteListViewModel else")
                setScreenState {
                    copy(error = true) // here we should implement a more sophisticated error handling mechanism but for this demo it does the trick
                }
            }

        }
    }

    override suspend fun handleActions(action: NoteListScreenActions) {
       when(action) {
           is NoteListScreenActions.OnNoteSelectedClick ->  {} // load
           is NoteListScreenActions.OnNewNoteClick -> setEffect { NoteListScreenEffects.CreateNewNoteClicked }
           else -> {}
       }
    }
}

data class NoteListScreenState(
    val isLoading: Boolean = false,
    val notes: List<NoteEntity> = emptyList(),
    val error: Boolean = false
): ScreenState

sealed class NoteListScreenEffects : Effect{
    object CreateNewNoteClicked : NoteListScreenEffects()
}

sealed class NoteListScreenActions : Action {
    object OnNewNoteClick : NoteListScreenActions()
    data class OnNoteSelectedClick(val note: NoteEntity) : NoteListScreenActions()
}
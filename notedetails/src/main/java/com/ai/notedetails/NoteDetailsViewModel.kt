package com.ai.notedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_android_data.DispatcherProvider
import com.ai.common_domain.entities.NoteEntity
import com.ai.common_domain.usecase.GetNoteById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject
constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getNoteById: GetNoteById,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<NoteDetailsScreenState, NoteDetailsScreenAction, NoteDetailsScreenEffect > () {


    init {
        savedStateHandle.get<String>(NOTE_ID_KEY)?.let { noteId ->
            viewModelScope.launch(dispatcherProvider.io()) {
                getNoteById(noteId)
            }
        }
    }

    override fun createInitialScreenSate(): NoteDetailsScreenState  = NoteDetailsScreenState()

    override suspend fun handleActions(action: NoteDetailsScreenAction) {
        when(action) {
            is NoteDetailsScreenAction.GetRelatedNoted -> {}
            is NoteDetailsScreenAction.CreateNote -> {}
            is NoteDetailsScreenAction.UpdateNote -> {}
        }
    }

    companion object {
        private const val NOTE_ID_KEY = "noteId"
    }
}


data class NoteDetailsScreenState(
    val isLoading: Boolean = false
): ScreenState

sealed class NoteDetailsScreenAction : Action {
    data class CreateNote(val note: NoteEntity) : NoteDetailsScreenAction()
    data class UpdateNote(val note: NoteEntity) : NoteDetailsScreenAction()
    object GetRelatedNoted : NoteDetailsScreenAction()
}

sealed class NoteDetailsScreenEffect : Effect {

}
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
) : BaseViewModel<NoteDetailsScreenState, NoteDetailsScreenActions, NoteDetailsScreenEffect > () {


    init {
        savedStateHandle.get<String>(NOTE_ID_KEY)?.let { noteId ->
            viewModelScope.launch(dispatcherProvider.io()) {
                getNoteById(noteId)
            }
        }
    }

    override fun createInitialScreenSate(): NoteDetailsScreenState  = NoteDetailsScreenState()

    override suspend fun handleActions(action: NoteDetailsScreenActions) {
        when(action) {
            is NoteDetailsScreenActions.GetRelatedNoted -> {}
            is NoteDetailsScreenActions.CreateNote -> {}
            is NoteDetailsScreenActions.UpdateNote -> {}
        }
    }

    companion object {
        private const val NOTE_ID_KEY = "noteId"
    }
}


data class NoteDetailsScreenState(
    val isLoading: Boolean = false,
    val error: Boolean = false // this should be a class but for this sample should be enough
): ScreenState

sealed class NoteDetailsScreenActions : Action {
    data class CreateNote(val note: NoteEntity) : NoteDetailsScreenActions()
    data class UpdateNote(val note: NoteEntity) : NoteDetailsScreenActions()
    object GetRelatedNoted : NoteDetailsScreenActions()
}

sealed class NoteDetailsScreenEffect : Effect {

}
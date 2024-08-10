package com.ai.notedetails

import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import com.ai.common_domain.entities.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject
constructor(

) : BaseViewModel<NoteDetailsScreenState, NoteDetailsScreenAction, NoteDetailsScreenEffect > () {

    override fun createInitialScreenSate(): NoteDetailsScreenState  = NoteDetailsScreenState()

    override suspend fun handleActions(action: NoteDetailsScreenAction) {
        when(action) {
            is NoteDetailsScreenAction.GetRelatedNoted -> {}
            is NoteDetailsScreenAction.CreateNote -> {}
            is NoteDetailsScreenAction.UpdateNote -> {}
        }
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
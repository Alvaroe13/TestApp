package com.ai.notelist

import com.ai.common.viewmodel.Action
import com.ai.common.viewmodel.BaseViewModel
import com.ai.common.viewmodel.Effect
import com.ai.common.viewmodel.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
) : BaseViewModel<NoteListScreenState, NoteListScreenActions, NoteListScreenEffects> (){


    override fun createInitialScreenSate(): NoteListScreenState {
        TODO("Not yet implemented")
    }

    override suspend fun handleActions(action: NoteListScreenActions) {
        TODO("Not yet implemented")
    }
}

data class NoteListScreenState(
    var isLoading: Boolean = false,
): ScreenState

sealed class NoteListScreenEffects : Effect{

}

sealed class NoteListScreenActions : Action {

}
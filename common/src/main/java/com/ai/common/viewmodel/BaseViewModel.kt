package com.ai.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S: ScreenState, A: Action, E: Effect>: ViewModel() {

    private val initialScreenState: S by lazy { createInitialScreenSate() }
    protected abstract fun createInitialScreenSate(): S
    protected val currentScreenState: S get() = screenState.value

    private val _screenState: MutableStateFlow<S> by lazy { MutableStateFlow(initialScreenState) }
    val screenState = _screenState.asStateFlow()

    private val _actions = MutableSharedFlow<A>()
    protected val actions = _actions.asSharedFlow()

    private val _effect = Channel<E>()
    val effect = _effect.receiveAsFlow()

    init{
        viewModelScope.launch {
            actions.collect {
                if(it is ClickAction) {
                    //Here we could apply some sort of a mechanism to generating repeated actions due to multitouch
                    handleActions(it)
                } else {
                    handleActions(it)
                }
            }
        }
    }

    protected abstract suspend fun handleActions(action: A)
    fun setAction(action: A) = viewModelScope.launch { _actions.emit(action) }

    protected fun setEffect(effect: ()-> E) = viewModelScope.launch { _effect.send(effect()) }

    protected fun setScreenState(state: S.()-> S) {
        _screenState.value = currentScreenState.state()
    }

}
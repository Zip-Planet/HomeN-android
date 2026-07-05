package com.devndev.homen.ui.main.board.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class BoardContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()

    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false
    ): ViewState

    sealed class Effect: ViewSideEffect {

    }
}
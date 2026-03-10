package com.devndev.homen.ui.main.homeintro.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class HomeIntroContract {
    sealed class Event : ViewEvent {
        data object OnCreateHomeClick : Event()
        data object OnJoinHomeClick : Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToCreateHome : Effect()
        data object NavigateToJoinHome : Effect()
    }
}

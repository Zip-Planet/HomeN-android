package com.devndev.homen.ui.main.homeentry.join.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class CodeEnterContract {
    sealed class Event : ViewEvent {
        data class OnCodeChanged(val code: String) : Event()
        data object OnJoinClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val code: String = "",
        val isLoading: Boolean = false,
        val isError: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToMain : Effect()
        data object PopBackStack : Effect()
    }
}

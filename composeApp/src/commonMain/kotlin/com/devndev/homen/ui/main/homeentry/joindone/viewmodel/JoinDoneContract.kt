package com.devndev.homen.ui.main.homeentry.joindone.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class JoinDoneContract {
    sealed class Event : ViewEvent {
        data object OnConfirmClick : Event()
    }

    data class State(
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToHome : Effect()
    }
}

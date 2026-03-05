package com.devndev.homen.ui.main.homeentry.joinconfirm.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class JoinConfirmContract {
    sealed class Event : ViewEvent {
        data object OnJoinClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val homeName: String = "",
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToDone : Effect()
        data object PopBackStack : Effect()
    }
}

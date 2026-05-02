package com.devndev.homen.ui.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class MainContract {

    sealed class Event : ViewEvent {
        data object OnMainNav: Event()
    }

    data class State(
        val hasHome: Boolean? = null,
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
    }
}

package com.devndev.homen.ui.main.home.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class HomeContract {
    sealed class Event: ViewEvent {

    }

    data class State(
        val isLoading: Boolean = false,
        val homeIcon: Int = 0,
        val homeName: String = "",
        val totalMember: Int = 0,
        val totalChore: Int = 0,
        val completedChore: Int = 0,
        val mvpName: String = "",
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavToBoard: Effect()
    }
}
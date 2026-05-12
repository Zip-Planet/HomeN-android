package com.devndev.homen.ui.main.home.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class HomeContract {
    sealed class Event: ViewEvent {
        data class OnMemberSelected(val name: String): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val homeIcon: Int = 1,
        val homeName: String = "누리빌",
        val totalMember: Int = 3,
        val totalChore: Int = 0,
        val completedChore: Int = 0,
        val mvpName: String = "김치투다리우동",
        val members: List<String> = listOf("나", "김치투다리우동", "김수환"),
        val selectedMember: String = "나"
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavToBoard: Effect()
    }
}
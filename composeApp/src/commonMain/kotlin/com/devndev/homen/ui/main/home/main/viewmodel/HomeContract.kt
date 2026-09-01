package com.devndev.homen.ui.main.home.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Member

class HomeContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()
        data class OnMemberSelected(val member: Member): Event()
        data object OnChoreManageClick: Event()
        data object OnCreateAssignmentClick: Event()
    }

    data class State(
        val mainIsLoading: Boolean = true,
        val isLoading: Boolean = false,
        val homeIcon: Int = 0,
        val homeName: String = "",
        val totalMember: Int = 0,
        val totalChore: Int = 0,
        val completedChore: Int = 0,
        val mvpName: String = "김치투다리우동",
        val members: List<Member> = emptyList(),
        val selectedMember: Member? = null,
        val choreExist: Boolean = false
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBoard: Effect()
        data object NavigateToChoreManage: Effect()
        data object NavigateToAssignment: Effect()
    }
}
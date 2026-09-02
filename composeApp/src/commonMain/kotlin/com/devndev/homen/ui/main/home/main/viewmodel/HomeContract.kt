package com.devndev.homen.ui.main.home.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.core.domain.model.home.Member
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentTab

class HomeContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()
        data class OnMemberSelected(val member: Member, val index: Int): Event()
        data object OnChoreManageClick: Event()
        data object OnCreateAssignmentClick: Event()
        data object OnAssignmentClick: Event()
        data class OnCompleteClick(val assignment: AssignmentItem): Event()
        data class OnCompleteCancelClick(val assignment: AssignmentItem): Event()
    }

    data class State(
        val mainIsLoading: Boolean = true,
        val isLoading: Boolean = false,
        val homeIcon: Int = 0,
        val homeName: String = "",
        val totalMember: Int = 0,
        val totalChore: Int = 0,
        val completedChore: Int = 0,
        val mvpName: String = "",
        val mvpPoint: Int = 0,
        val members: List<Member> = emptyList(),
        val selectedMember: Member? = null,
        val choreExist: Boolean = false,
        val assignmentStatus: String = "",
        val assignment: Assignment? = null,
        val selectedAssignments: List<AssignmentItem> = emptyList(),
        val selectedIndex: Int = 0
    ): ViewState {
        val progressRate: Int = if (totalChore > 0) (completedChore * 100) / totalChore else 0
        val isMine: Boolean = selectedIndex == 0
    }

    sealed class Effect: ViewSideEffect {
        data object NavigateToBoard: Effect()
        data object NavigateToChoreManage: Effect()
        data class NavigateToAssignment(val isThisWeek: Boolean): Effect()
    }
}

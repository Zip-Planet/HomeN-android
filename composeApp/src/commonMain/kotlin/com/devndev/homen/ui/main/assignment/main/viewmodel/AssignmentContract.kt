package com.devndev.homen.ui.main.assignment.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.model.home.AssignmentItem
import com.devndev.homen.core.domain.model.home.MemberPoint

class AssignmentContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data class OnTabSelected(val tab: AssignmentTab) : Event()
        data class OnSelectedMember(val member: String) : Event()
        data object OnAddChoreClick : Event()
        data object OnCreateAssignmentClick : Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val selectedTab: AssignmentTab = AssignmentTab.THIS_WEEK,
        val screenType: AssignmentScreenType = AssignmentScreenType.NONE,
        val isManager: Boolean = false,
        val memberPoints: List<MemberPoint> = emptyList(),
        val assignment: Assignment? = null,
        val selectedMember: String = "전체",
        val selectedAssignments: List<AssignmentItem> = emptyList(),
    ) : ViewState {
        val isAddButtonExist = selectedTab == AssignmentTab.THIS_WEEK && when (screenType) {
            AssignmentScreenType.CREATE_ASSIGNMENT -> true
            AssignmentScreenType.ADD_CHORE -> !isManager
            else -> false
        }
    }

    sealed class Effect : ViewSideEffect {
        data object NavigateToChoreManage : Effect()
    }

}

enum class AssignmentTab(val title: String) {
    THIS_WEEK("이번 주"),
    NEXT_WEEK("다음 주"),
    HISTORY("히스토리")
}

enum class AssignmentScreenType {
    NONE,
    ADD_CHORE,
    CREATE_ASSIGNMENT,
    ASSIGNMENT
}

enum class AssignmentStatus(val status: String) {
    NONE("none"),
    PROPOSED("proposed"),
    CONFIRMED("confirmed"),
    EXPIRED("expired");

    companion object {
        fun fromValue(value: String): AssignmentStatus {
            return entries.find { it.status == value } ?: NONE
        }
    }
}
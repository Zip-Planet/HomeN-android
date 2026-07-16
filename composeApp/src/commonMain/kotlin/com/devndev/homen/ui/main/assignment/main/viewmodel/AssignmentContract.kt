package com.devndev.homen.ui.main.assignment.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class AssignmentContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data class OnTabSelected(val tab: AssignmentTab) : Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val selectedTab: AssignmentTab = AssignmentTab.THIS_WEEK,
        val screenType: AssignmentScreenType = AssignmentScreenType.NONE,
        val isManager: Boolean = false
    ) : ViewState {
        val isAddButtonExist = selectedTab == AssignmentTab.THIS_WEEK && when (screenType) {
            AssignmentScreenType.CREATE_ASSIGNMENT -> true
            AssignmentScreenType.ADD_CHORE -> !isManager
            else -> false
        }
    }

    sealed class Effect : ViewSideEffect {

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
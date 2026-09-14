package com.devndev.homen.ui.main.board.help.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.AssignmentItem

class BoardHelpContract {
    sealed class Event: ViewEvent {
        data object OnInit : Event()
        data class OnAssignmentSelected(val assignment: AssignmentItem): Event()
        data class OnHelpMessageChange(val message: String): Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val weekDay: String = "",
        val assignments: List<AssignmentItem> = emptyList(),
        val isExpandable: Boolean = true,
        val selectedAssignment: AssignmentItem? = null,
        val helpMessage: String = ""
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
    }
}
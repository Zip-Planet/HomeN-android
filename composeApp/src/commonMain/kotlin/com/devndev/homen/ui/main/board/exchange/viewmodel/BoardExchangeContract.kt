package com.devndev.homen.ui.main.board.exchange.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.AssignmentItem

class BoardExchangeContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data class OnMyAssignmentSelected(val assignment: AssignmentItem) : Event()
        data class OnTargetAssignmentSelected(val assignment: AssignmentItem) : Event()
        data class OnExchangeMessageChange(val message: String) : Event()
        data object OnRequestExchangeClick : Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val weekDay: String = "",
        val myAssignments: List<AssignmentItem> = emptyList(),
        val targetAssignments: List<AssignmentItem> = emptyList(),
        val selectedMyAssignment: AssignmentItem? = null,
        val selectedTargetAssignment: AssignmentItem? = null,
        val exchangeMessage: String = ""
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToBack : Effect()
    }
}

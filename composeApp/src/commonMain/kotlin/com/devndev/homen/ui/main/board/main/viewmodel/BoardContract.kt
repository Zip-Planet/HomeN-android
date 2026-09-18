package com.devndev.homen.ui.main.board.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.board.BoardCard

class BoardContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()
        data object OnRewardClick: Event()
        data object OnAssignmentClick: Event()
        data object OnRequestHelpClick: Event()
        data class OnDeleteHelpClick(val id: Int): Event()
        data class OnUndoDelete(val card: BoardCard, val index: Int): Event()
        data class OnDeleteConfirm(val id: Int): Event()
        data class OnAcceptHelp(val id: Int): Event()
        data class OnUndoAccept(val id: Int, val oldStatus: String): Event()
        data class OnAcceptConfirm(val id: Int): Event()
        data object OnDispose: Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val cards: List<BoardCard> = emptyList(),
        val myName: String = "",
        val myProfileImage: Int? = null
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToReward: Effect()
        data object NavigateToAssignment: Effect()
        data object NavigateToHelp: Effect()
        data class ShowDeleteSnackBar(val card: BoardCard, val index: Int): Effect()
        data class ShowAcceptSnackBar(val id: Int, val oldStatus: String): Effect()
    }
}
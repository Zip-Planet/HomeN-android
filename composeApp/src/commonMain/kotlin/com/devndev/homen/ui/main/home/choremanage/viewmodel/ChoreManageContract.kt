package com.devndev.homen.ui.main.home.choremanage.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Chore

class ChoreManageContract {
    sealed class Event: ViewEvent {
        data object OnInit : Event()
        data object OnBackClick: Event()
        data object OnDispose: Event()
        data class OnTooltipClick(val show: Boolean, val isEmptyChore: Boolean): Event()
        data class OnOptionClick(val option: Int): Event()
        data object OnNextButtonClick: Event()
        data object OnAddButtonClick: Event()
        data class OnDeleteClick(val id: Int): Event()
        data class OnUndoDelete(val chore: Chore, val index: Int): Event()
        data class OnDeleteConfirm(val id: Int): Event()
        data class OnEditClick(val id: Int): Event()
        data class OnChoreClick(val id: Int): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val chores: List<Chore> = emptyList(),
        val homeName: String = "",
        val isEmptyChore: Boolean = true,
        val isEmptyChoreTooltipShow: Boolean = true,
        val isNotEmptyChoreTooltipShow: Boolean = true,
        val selectedOption: Int = 0
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
        data object NavigateToCrateChore: Effect()
        data class NavigateToEditChore(val id: Int): Effect()
        data class NavigateToChoreDetail(val id: Int): Effect()
        data object NavigateToStaterPack: Effect()
        data class ShowDeleteSnackBar(val chore: Chore, val index: Int): Effect()
    }
}
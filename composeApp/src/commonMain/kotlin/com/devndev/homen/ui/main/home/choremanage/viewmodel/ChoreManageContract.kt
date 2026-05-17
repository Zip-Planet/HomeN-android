package com.devndev.homen.ui.main.home.choremanage.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class ChoreManageContract {
    sealed class Event: ViewEvent {
        data object OnBackClick: Event()
        data class OnTooltipClick(val show: Boolean): Event()
        data class OnOptionClick(val option: Int): Event()
        data object OnNextButtonClick: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isEmptyChore: Boolean = true,
        val isEmptyChoreTooltipShow: Boolean = true,
        val selectedOption: Int = 0
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
        data object NavigateToCrateChore: Effect()
    }
}
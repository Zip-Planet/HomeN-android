package com.devndev.homen.ui.main.homeintro.join.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class CodeEnterContract {
    sealed class Event : ViewEvent {
        data class OnCodeChanged(val code: String) : Event()
        data object OnJoinClick : Event()
        data object OnBackClick : Event()
        data class OnTooltipToggle(val show: Boolean) : Event()
    }

    data class State(
        val codeEnterStep: CodeEnterStep = CodeEnterStep.NONE,
        val code: String = "",
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val showTooltip: Boolean = true
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToConfirm : Effect()
        data object PopBackStack : Effect()
    }
}

enum class CodeEnterStep {
    NONE,
    INVALID
}
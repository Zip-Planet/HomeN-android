package com.devndev.homen.ui.intro.register.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class RegisterContract {
    sealed class Event : ViewEvent {
        data class OnNicknameChanged(val nickname: String) : Event()
        data class OnAvatarSelected(val index: Int) : Event()
        data object OnNextClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val currentStep: RegisterStep = RegisterStep.NICKNAME,
        val nickname: String = "",
        val selectedAvatarIndex: Int? = null,
        val isLoading: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToMain : Effect()
        data object PopBackStack : Effect()
    }
}

enum class RegisterStep {
    NICKNAME,
    AVATAR
}
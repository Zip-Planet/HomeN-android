package com.devndev.homen.ui.intro.login

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class LoginContract {

    sealed class Event : ViewEvent {
        data object OnKakaoLoginClick : Event()
        data object OnAppleLoginClick : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val hasHome: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToMain : Effect()
        data object NavigateToRegister : Effect()
    }
}

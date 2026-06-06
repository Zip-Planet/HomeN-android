package com.devndev.homen.ui.intro.splash.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.ui.intro.register.viewmodel.RegisterContract.Event

class SplashContract {
    sealed class Event : ViewEvent {
        data object OnSplashFinished : Event()
        data object OnCheckToken: Event()
    }

    data class State(
        val isAllWordsUp: Boolean = false,
        val hasHome: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToMain : Effect()
        data object NavigateToLogin : Effect()
    }
}


package com.devndev.homen.ui.intro.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.usecase.splash.CheckTokenUseCase
import kotlinx.coroutines.launch


class SplashViewModel(
    private val checkTokenUseCase: CheckTokenUseCase
) : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun setInitialState() = SplashContract.State()

    override fun handleEvents(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.OnSplashFinished -> {
                setState { copy(isAllWordsUp = true)}
            }

            SplashContract.Event.OnCheckToken -> {
                checkToken()
            }
        }
    }

    private fun checkToken() {
        viewModelScope.launch {
            val isTokenExist = checkTokenUseCase()
            when (isTokenExist) {
                true -> setEffect { SplashContract.Effect.NavigateToMain }
                false -> setEffect { SplashContract.Effect.NavigateToLogin }
            }
        }
    }
}

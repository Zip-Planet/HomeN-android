package com.devndev.homen.ui.intro.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetHasHomeUseCase
import com.devndev.homen.core.domain.usecase.splash.CheckTokenUseCase
import kotlinx.coroutines.launch


class SplashViewModel(
    private val checkTokenUseCase: CheckTokenUseCase,
    private val getHasHomeUseCase: GetHasHomeUseCase
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
                true -> {
                    getHasHome()
                }
                false -> setEffect { SplashContract.Effect.NavigateToLogin }
            }
        }
    }

    private fun getHasHome() {
        viewModelScope.launch {
            when (val result = getHasHomeUseCase()) {
                is ApiResult.Success -> {
                    setState { copy(hasHome = result.data) }
                    setEffect { SplashContract.Effect.NavigateToMain }
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
        }
    }
}

package com.devndev.homen.ui.main.homeintro.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.auth.ClearTokenUseCase
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import kotlinx.coroutines.launch

class HomeIntroViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : BaseViewModel<HomeIntroContract.Event, HomeIntroContract.State, HomeIntroContract.Effect>() {

    override fun setInitialState() = HomeIntroContract.State()

    override fun handleEvents(event: HomeIntroContract.Event) {
        when (event) {
            HomeIntroContract.Event.OnCreateHomeClick -> {
                setEffect { HomeIntroContract.Effect.NavigateToCreateHome }
            }
            HomeIntroContract.Event.OnJoinHomeClick -> {
                setEffect { HomeIntroContract.Effect.NavigateToJoinHome }
            }

            HomeIntroContract.Event.OnLogoutClick -> {
                logout()
            }
        }
    }

    private fun logout() {
        setState { copy(isLoading = true)}
        viewModelScope.launch {
            val result = logoutUseCase()
            when (result) {
                is ApiResult.Success -> {
                    clearTokenUseCase()
                    setEffect { HomeIntroContract.Effect.NavigateToSplash }
                }
                is ApiResult.Error -> {
                    // TODO error 처리
                }
                ApiResult.NetworkError -> {
                    // TODO network error 처리
                }
            }
            setState { copy(isLoading = false) }
        }
    }
}

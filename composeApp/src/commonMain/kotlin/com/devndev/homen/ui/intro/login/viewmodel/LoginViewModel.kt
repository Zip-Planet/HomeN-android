package com.devndev.homen.ui.intro.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.usecase.auth.SocialLoginUseCase
import com.devndev.homen.ui.intro.login.LoginContract
import kotlinx.coroutines.launch

class LoginViewModel(private val socialLoginUseCase: SocialLoginUseCase) :
    BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            LoginContract.Event.OnKakaoLoginClick -> {
                loginWithKakao()
            }
            LoginContract.Event.OnAppleLoginClick -> {
                loginWithApple()
            }
        }
    }

    private fun loginWithKakao() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val result = socialLoginUseCase.loginWithKakao()) {
                is SocialAuthResult.Success -> {
                    setEffect { LoginContract.Effect.NavigateToMain(result.data.accessToken) }
                }
                is SocialAuthResult.UserCancelled, is SocialAuthResult.Error -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun loginWithApple() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val result = socialLoginUseCase.loginWithApple()) {
                is SocialAuthResult.Success -> {
                    setEffect { LoginContract.Effect.NavigateToMain(result.data.idToken) }
                }
                is SocialAuthResult.UserCancelled, is SocialAuthResult.Error -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }
}

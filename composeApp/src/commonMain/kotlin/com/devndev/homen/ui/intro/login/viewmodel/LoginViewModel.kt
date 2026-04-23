package com.devndev.homen.ui.intro.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.model.auth.SocialToken
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.auth.KakaoLoginToServerUseCase
import com.devndev.homen.core.domain.usecase.auth.SaveTokensUseCase
import com.devndev.homen.core.domain.usecase.auth.SocialLoginUseCase
import com.devndev.homen.ui.intro.login.LoginContract
import kotlinx.coroutines.launch

class LoginViewModel(
    private val socialLoginUseCase: SocialLoginUseCase,
    private val kakaoLoginUseCase: KakaoLoginToServerUseCase,
    private val saveTokensUseCase: SaveTokensUseCase
) :
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
            val result = socialLoginUseCase.loginWithKakao()
            kakaoLoginToServer(result)
            setState { copy(isLoading = false) }
        }
    }

    private fun loginWithApple() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }

            when (val result = socialLoginUseCase.loginWithApple()) {
                is SocialAuthResult.Success -> {
                    setEffect { LoginContract.Effect.NavigateToRegister }
                }

                is SocialAuthResult.UserCancelled, is SocialAuthResult.Error -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }

    private fun kakaoLoginToServer(socialAuthResult: SocialAuthResult<KakaoUser>) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            when (socialAuthResult) {
                is SocialAuthResult.Success -> {
                    val result = kakaoLoginUseCase(SocialToken(socialAuthResult.data.accessToken))
                    when (result) {
                        is ApiResult.Success -> {
                            if (result.data.isProfileSet) {
                                saveTokens(result.data.accessToken, result.data.refreshToken, isPermanent = true)
                                setEffect { LoginContract.Effect.NavigateToMain }
                            } else {
                                saveTokens(result.data.accessToken, result.data.refreshToken, isPermanent = false)
                                setEffect { LoginContract.Effect.NavigateToRegister }
                            }
                        }
                        is ApiResult.Error -> {
//                            setState { copy(error = "로그인 실패: ${result.message}") }
                        }
                        is ApiResult.NetworkError -> {
//                            setState { copy(error = "네트워크 상태를 확인해주세요.") }
                        }
                    }
                }

                is SocialAuthResult.UserCancelled, is SocialAuthResult.Error -> {

                }

            }
            setState { copy(isLoading = false) }
        }
    }

    private fun saveTokens(accessToken: String, refreshToken: String, isPermanent: Boolean){
        viewModelScope.launch {
            saveTokensUseCase(accessToken, refreshToken, isPermanent)
        }
    }
}

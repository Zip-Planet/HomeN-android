package com.devndev.homen.ui.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetHasHomeUseCase
import com.devndev.homen.core.domain.usecase.user.RegisterDeviceUseCase
import com.devndev.homen.core.domain.util.FcmTokenProvider
import kotlinx.coroutines.launch

/**
 * 메인 네비게이션 및 전역 상태를 관리
 */
class MainViewModel(
    private val getHasHomeUseCase: GetHasHomeUseCase,
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val fcmTokenProvider: FcmTokenProvider
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    override fun setInitialState() = MainContract.State()

    override fun handleEvents(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnMainNav -> {
                getHasHome()
                registerDevice()
            }
        }
    }

    private fun registerDevice() {
        viewModelScope.launch {
            try {
                val token = fcmTokenProvider.getFcmToken()
                val platform = fcmTokenProvider.getPlatform()
                if (!token.isNullOrEmpty()) {
                    registerDeviceUseCase(registrationId = token, platform = platform)
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun getHasHome() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            when (val result = getHasHomeUseCase()) {
                is ApiResult.Success -> {
                    if (result.data) {
                        setState { copy(hasHome = true) }
                    } else {
                        setState { copy(hasHome = false) }
                    }
                }
                is ApiResult.Error -> {

                }
                ApiResult.NetworkError -> {

                }
            }
            setState { copy(isLoading = false) }
        }
    }

    companion object {
        val TAG = MainViewModel::class.simpleName
    }
}

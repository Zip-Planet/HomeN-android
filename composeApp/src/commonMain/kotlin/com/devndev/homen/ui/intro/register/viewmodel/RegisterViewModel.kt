package com.devndev.homen.ui.intro.register.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.user.UpdateProfile
import com.devndev.homen.core.domain.usecase.user.UpdateProfileUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>() {

    override fun setInitialState() = RegisterContract.State()

    override fun handleEvents(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.OnNicknameChanged -> {
                setState { copy(nickname = event.nickname) }
            }
            is RegisterContract.Event.OnAvatarSelected -> {
                setState { copy(selectedAvatar = event.avatarType) }
            }
            RegisterContract.Event.OnNextClick -> {
                onNextStep()
            }
            RegisterContract.Event.OnBackClick -> {
                onBackPressed()
            }
        }
    }

    private fun onNextStep() {
        val currentState = viewState.value
        when (currentState.currentStep) {
            RegisterStep.NICKNAME -> {
                if (currentState.nickname.isNotEmpty()) {
                    setState { copy(currentStep = RegisterStep.AVATAR) }
                }
            }
            RegisterStep.AVATAR -> {
                if (currentState.selectedAvatar != null) {
                    registerProfile()
                }
            }
        }
    }

    private fun registerProfile() {
        viewModelScope.launch {
            val result = updateProfileUseCase(
                UpdateProfile(
                    name = viewState.value.nickname,
                    profileImage = viewState.value.selectedAvatar!!.id
                )
            )
            when (result) {
                is ApiResult.Success -> {
                    setEffect { RegisterContract.Effect.NavigateToMain }
                }

                is ApiResult.Error -> {
                    // TODO 에러 처리
                }
                ApiResult.NetworkError -> {
                    // TODO network 에러 처리
                }
            }
        }
    }

    private fun onBackPressed() {
        if (viewState.value.currentStep == RegisterStep.AVATAR) {
            setState { copy(currentStep = RegisterStep.NICKNAME) }
            setState { copy(selectedAvatar = null) }
        } else {
            setEffect { RegisterContract.Effect.PopBackStack }
        }
    }
}

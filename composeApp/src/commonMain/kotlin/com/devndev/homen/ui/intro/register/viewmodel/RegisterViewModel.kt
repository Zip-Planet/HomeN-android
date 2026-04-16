package com.devndev.homen.ui.intro.register.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class RegisterViewModel : BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>() {

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
                    setEffect { RegisterContract.Effect.NavigateToMain }
                    // TODO: 회원가입 API 호출 시 currentState.selectedAvatar.id 전달
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

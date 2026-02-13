package com.devndev.homen.ui.intro.register.viewmodel

import androidx.lifecycle.ViewModel
import com.devndev.homen.ui.intro.register.RegisterStep
import com.devndev.homen.ui.intro.register.RegisterUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onNicknameChanged(newNickname: String) {
        _uiState.update { it.copy(nickname = newNickname) }
    }

    fun onAvatarSelected(index: Int?) {
        _uiState.update { it.copy(selectedAvatarIndex = index) }
    }

    fun onNextStep(onNavToMain: () -> Unit) {
        val currentState = _uiState.value
        when (currentState.currentStep) {
            RegisterStep.NICKNAME -> {
                if (currentState.nickname.isNotEmpty()) {
                    _uiState.update { it.copy(currentStep = RegisterStep.AVATAR) }
                }
            }
            RegisterStep.AVATAR -> {
                if (currentState.selectedAvatarIndex != null) {
                    // 가입 완료 로직 후 메인 이동
                    onNavToMain()
                }
            }
        }
    }

    fun onBackPressed(onNavBack: () -> Unit) {
        if (_uiState.value.currentStep == RegisterStep.AVATAR) {
            _uiState.update { it.copy(currentStep = RegisterStep.NICKNAME) }
        } else {
            onNavBack()
        }
    }
}

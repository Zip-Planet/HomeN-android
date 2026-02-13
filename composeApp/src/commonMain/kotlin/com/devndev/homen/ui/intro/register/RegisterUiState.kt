package com.devndev.homen.ui.intro.register

data class RegisterUiState(
    val currentStep: RegisterStep = RegisterStep.NICKNAME,
    val nickname: String = "",
    val selectedAvatarIndex: Int? = null,
    val isLoading: Boolean = false
)

enum class RegisterStep {
    NICKNAME,
    AVATAR
}

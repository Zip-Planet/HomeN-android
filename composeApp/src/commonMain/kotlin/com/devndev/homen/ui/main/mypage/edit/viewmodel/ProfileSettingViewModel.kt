package com.devndev.homen.ui.main.mypage.edit.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.user.UpdateProfile
import com.devndev.homen.core.domain.usecase.user.UpdateProfileUseCase
import kotlinx.coroutines.launch

class ProfileSettingViewModel(
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel<ProfileSettingContract.Event, ProfileSettingContract.State, ProfileSettingContract.Effect>() {

    override fun setInitialState() = ProfileSettingContract.State()

    override fun handleEvents(event: ProfileSettingContract.Event) {
        when (event) {
            is ProfileSettingContract.Event.OnInit -> {
                val avatarType = AvatarType.fromId(event.avatarId)
                setState {
                    copy(
                        nickname = event.nickname,
                        selectedAvatar = avatarType,
                        isSaveEnabled = event.nickname.isNotBlank()
                    )
                }
            }

            is ProfileSettingContract.Event.OnNicknameChanged -> {
                setState {
                    copy(
                        nickname = event.nickname,
                        isSaveEnabled = event.nickname.isNotBlank() && selectedAvatar != null
                    )
                }
            }

            is ProfileSettingContract.Event.OnAvatarSelected -> {
                setState {
                    copy(
                        selectedAvatar = event.avatarType,
                        isSaveEnabled = nickname.isNotBlank()
                    )
                }
            }

            ProfileSettingContract.Event.OnSaveClick -> {
                updateProfile()
            }

            ProfileSettingContract.Event.OnBackClick -> {
                setEffect { ProfileSettingContract.Effect.PopBackStack }
            }
        }
    }

    private fun updateProfile() {
        val avatar = viewState.value.selectedAvatar ?: return
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = updateProfileUseCase(
                UpdateProfile(
                    name = viewState.value.nickname,
                    profileImage = avatar.id
                )
            )
            when (result) {
                is ApiResult.Success -> {
                    setEffect { ProfileSettingContract.Effect.SaveSuccess }
                }

                is ApiResult.Error -> {
                    // TODO Error handling
                }

                ApiResult.NetworkError -> {
                    // TODO Network error handling
                }
            }
            setState { copy(isLoading = false) }
        }
    }
}

package com.devndev.homen.ui.main.mypage.edit.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.AvatarType

class ProfileSettingContract {
    sealed class Event : ViewEvent {
        data class OnInit(val nickname: String, val avatarId: Int) : Event()
        data class OnNicknameChanged(val nickname: String) : Event()
        data class OnAvatarSelected(val avatarType: AvatarType) : Event()
        data object OnSaveClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val nickname: String = "",
        val selectedAvatar: AvatarType? = null,
        val isLoading: Boolean = false,
        val isSaveEnabled: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object PopBackStack : Effect()
        data object SaveSuccess : Effect()
    }
}

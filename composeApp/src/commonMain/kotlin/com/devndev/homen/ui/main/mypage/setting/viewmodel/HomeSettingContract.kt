package com.devndev.homen.ui.main.mypage.setting.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Member

class HomeSettingContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data object OnLeaveClick : Event()
        data object OnDelegateManagerClick : Event()
        data object OnConfirmLeave : Event()
        data object OnConfirmDisband : Event()
        data object OnDismissPopup : Event()
        data object OnBackClick : Event()
    }

    enum class SettingPopupType {
        NONE,
        LEAVE,
        CANNOT_LEAVE,
        DISBAND
    }

    data class State(
        val isLoading: Boolean = false,
        val homeName: String = "",
        val homeImageId: Int = 1,
        val myName: String = "",
        val isManager: Boolean = false,
        val members: List<Member> = emptyList(),
        val activePopup: SettingPopupType = SettingPopupType.NONE
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object PopBackStack : Effect()
        data object NavigateToHomeIntro : Effect()
        data object NavigateToDelegateManager : Effect()
    }
}

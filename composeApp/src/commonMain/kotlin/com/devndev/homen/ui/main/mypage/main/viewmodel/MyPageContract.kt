package com.devndev.homen.ui.main.mypage.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class MyPageContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data object OnProfileSettingClick : Event()
        data object OnHomeSettingClick : Event()
        data object OnCopyInviteCode : Event()
        data object OnShareInviteCode : Event()
        data object OnPushToggle : Event()
        data class OnSubPushToggle(val type: PushType) : Event()
        data object OnSupportClick : Event()
        data object OnLogoutClick : Event()
    }

    enum class PushType {
        HOME, ASSIGNMENT, BOARD, REWARD, REPORT
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val nickname: String = "",
        val avatarId: Int? = null,
        val homeName: String = "",
        val homeRole: Int? = null,
        val inviteCode: String = "",
        val isPushEnabled: Boolean = false,
        val isPushDetailExpanded: Boolean = false,
        val isHomeAlarmEnabled: Boolean = true,
        val isAssignmentAlarmEnabled: Boolean = true,
        val isBoardAlarmEnabled: Boolean = true,
        val isRewardAlarmEnabled: Boolean = true,
        val isReportAlarmEnabled: Boolean = true
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToProfileSetting : Effect()
        data object NavigateToHomeSetting : Effect()
        data object NavigateToSupport : Effect()
        data object NavigateToLogin : Effect()
        data class ShowToast(val message: String) : Effect()
        data class ShareInviteCode(val code: String) : Effect()
    }
}

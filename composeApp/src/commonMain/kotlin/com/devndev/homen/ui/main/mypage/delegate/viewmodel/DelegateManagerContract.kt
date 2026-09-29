package com.devndev.homen.ui.main.mypage.delegate.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Member

class DelegateManagerContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data class OnMemberSelect(val member: Member) : Event()
        data object OnDelegateClick : Event()
        data object OnConfirmDelegate : Event()
        data object OnDismissPopup : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val currentManagerName: String = "",
        val myProfileImage: Int? = null,
        val selectedMember: Member? = null,
        val candidates: List<Member> = emptyList(),
        val isShowPopup: Boolean = false,
        val isDelegateEnabled: Boolean = false
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object PopBackStack : Effect()
        data object DelegateSuccess : Effect()
    }
}

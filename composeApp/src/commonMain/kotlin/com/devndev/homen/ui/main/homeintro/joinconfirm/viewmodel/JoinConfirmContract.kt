package com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.home.Member

class JoinConfirmContract {
    sealed class Event : ViewEvent {
        data class OnInit(val code: String) : Event()
        data class OnJoinClick(val code: String) : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val homeName: String = "",
        val imageId: Int = 1,
        val createdAt: String = "",
        val members: List<Member> = emptyList(),
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToDone : Effect()
        data object PopBackStack : Effect()
    }
}

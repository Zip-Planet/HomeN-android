package com.devndev.homen.ui.main.homeintro.create.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.chore.StarterPackType
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.Reward

class CreateHomeContract {
    sealed class Event : ViewEvent {
        data class OnHomeNameChanged(val name: String) : Event()
        data class OnAvatarSelected(val avatarId: Int) : Event()
        data class OnPackSelected(val packType: StarterPackType) : Event()
        data class OnTooltipToggle(val show: Boolean) : Event()
        data object OnNextClick : Event()
        data object OnBackClick : Event()

        data object OnCreateChoreClick : Event()
        data object OnPreviewClick : Event()
        data class OnChoreChecked(val chore: Chore) : Event()

        // Reward 관련 이벤트
        data object OnAddRewardClick : Event()
        data class OnRemoveRewardClick(val index: Int) : Event()
        data class OnRewardNameChanged(val index: Int, val name: String) : Event()
        data class OnRewardPointChanged(val index: Int, val point: String) : Event()

        data object OnSkipClick : Event()
        data object OnCompleteClick : Event()
        data class OnInviteClick(val isShow: Boolean): Event()
        data object OnKakaoShare: Event()
        data object OnGeneralShare: Event()
    }

    data class State(
        val homeName: String = "",
        val avatarId: Int? = null,
        val selectedPack: StarterPackType? = null,
        val chores: List<Chore> = emptyList(),
        val selectedChores: List<Chore> = emptyList(),
        val rewards: List<Reward> = listOf(Reward()),
        val showTooltip: Boolean = true,
        val isLoading: Boolean = false,
        val isShowInvitePopup: Boolean = false,
        val inviteCode: String = ""
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavToNext : Effect()
        data object PopBackStack : Effect()
        data object NavToCreateChore : Effect()
        data object NavToPreview : Effect()
    }
}

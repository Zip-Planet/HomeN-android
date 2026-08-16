package com.devndev.homen.ui.main.reward.detail.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.reward.RewardDetail

class RewardDetailContract {
    sealed class Event : ViewEvent {
        data class OnInit(val rewardId: Int) : Event()
        data object OnBackClick : Event()
        data class OnNavToEditClick(val rewardId: Int, val reward: String?, val point: String?, val isEdit: Boolean = true): Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val rewardDetail: RewardDetail? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object NavigateToBack : Effect()
        data class NavigateToEditReward(val rewardId: Int, val reward: String?, val point: String?, val isEdit: Boolean = true) : Effect()
    }
}

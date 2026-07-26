package com.devndev.homen.ui.main.reward.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.reward.Reward

class RewardContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val rewards: List<Reward> = emptyList(),
        val myPoint: Int = 0,
        val claimableCount: Int = 0,
        val inProgressCount: Int = 0,
        val claimedCount: Int = 0
    ): ViewState {
        val isRewardExist = rewards.isNotEmpty()
    }

    sealed class Effect: ViewSideEffect {

    }
}
package com.devndev.homen.ui.main.reward.main.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.reward.Reward

class RewardContract {
    sealed class Event: ViewEvent {
        data object OnInit: Event()
        data object OnDispose: Event()
        data object OnCreateRewardClick: Event()
        data class OnEditClick(val reward: Reward): Event()
        data class OnDeleteClick(val id: Int): Event()
        data class OnUndoDelete(val reward: Reward, val index: Int): Event()
        data class OnDeleteConfirm(val id: Int): Event()
    }

    data class State(
        val mainIsLoading: Boolean = false,
        val isLoading: Boolean = false,
        val rewards: List<Reward> = emptyList(),
        val myPoint: Int = 0,
        val claimableCount: Int = 0,
        val inProgressCount: Int = 0,
        val claimedCount: Int = 0,
        val profileImage: Int = 0,
    ): ViewState {
        val isRewardExist = rewards.isNotEmpty()
    }

    sealed class Effect: ViewSideEffect {
        data class NavigateToRewardEdit(val rewardId: Int?, val reward: String?, val point: String?, val isEdit: Boolean): Effect()
        data class ShowDeleteSnackBar(val reward: Reward, val index: Int): Effect()
    }
}
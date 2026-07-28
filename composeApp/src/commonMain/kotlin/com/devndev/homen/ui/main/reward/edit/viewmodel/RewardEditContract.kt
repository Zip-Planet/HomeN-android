package com.devndev.homen.ui.main.reward.edit.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class RewardEditContract {
    sealed class Event: ViewEvent {
        data class OnSaveClick(val isEdit: Boolean): Event()
        data class OnRewardChange(val reward: String): Event()
        data class OnPointChange(val point: String): Event()
    }

    data class State(
        val reward: String = "",
        val point: String = "",
    ): ViewState {
        val isSaveButtonEnable: Boolean = reward.isNotEmpty() && point.isNotEmpty()
    }

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
    }
}
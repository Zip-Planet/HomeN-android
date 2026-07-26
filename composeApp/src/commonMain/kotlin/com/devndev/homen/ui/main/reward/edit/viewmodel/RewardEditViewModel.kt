package com.devndev.homen.ui.main.reward.edit.viewmodel

import com.devndev.homen.core.common.base.BaseViewModel

class RewardEditViewModel(

): BaseViewModel<RewardEditContract.Event, RewardEditContract.State, RewardEditContract.Effect>() {
    override fun setInitialState() = RewardEditContract.State()

    override fun handleEvents(event: RewardEditContract.Event) {
        when (event) {
            is RewardEditContract.Event.OnSaveClick -> {

            }

            is RewardEditContract.Event.OnPointChange -> {
                setState { copy(point = event.point) }
            }
            is RewardEditContract.Event.OnRewardChange -> {
                setState { copy(reward = event.reward) }
            }
        }
    }

}
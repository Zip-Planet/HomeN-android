package com.devndev.homen.ui.main.reward.edit.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.reward.CreateRewardUseCase
import kotlinx.coroutines.launch

class RewardEditViewModel(
    private val createRewardUseCase: CreateRewardUseCase,
) : BaseViewModel<RewardEditContract.Event, RewardEditContract.State, RewardEditContract.Effect>() {
    override fun setInitialState() = RewardEditContract.State()

    override fun handleEvents(event: RewardEditContract.Event) {
        when (event) {
            is RewardEditContract.Event.OnSaveClick -> {
                if (event.isEdit) {

                } else {
                    createReward()
                }
            }

            is RewardEditContract.Event.OnPointChange -> {
                setState { copy(point = event.point) }
            }

            is RewardEditContract.Event.OnRewardChange -> {
                setState { copy(reward = event.reward) }
            }
        }
    }

    fun createReward() {
        viewModelScope.launch {
            val result = createRewardUseCase(
                name = viewState.value.reward,
                goalPoint = viewState.value.point.toInt()
            )

            when (result) {
                is ApiResult.Success -> {
                    setEffect { RewardEditContract.Effect.NavigateToBack }
                }
                else -> {

                }
            }
        }
    }
}
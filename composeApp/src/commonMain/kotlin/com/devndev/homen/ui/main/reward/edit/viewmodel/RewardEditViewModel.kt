package com.devndev.homen.ui.main.reward.edit.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.reward.CreateRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.EditRewardUseCase
import kotlinx.coroutines.launch

class RewardEditViewModel(
    private val createRewardUseCase: CreateRewardUseCase,
    private val editRewardUseCase: EditRewardUseCase
) : BaseViewModel<RewardEditContract.Event, RewardEditContract.State, RewardEditContract.Effect>() {
    override fun setInitialState() = RewardEditContract.State()

    override fun handleEvents(event: RewardEditContract.Event) {
        when (event) {
            is RewardEditContract.Event.OnSaveClick -> {
                if (event.isEdit) {
                    editReward(event.id)
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

    private fun createReward() {
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

    private fun editReward(id: Int) {
        viewModelScope.launch {
            val result = editRewardUseCase(
                id,
                viewState.value.reward,
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
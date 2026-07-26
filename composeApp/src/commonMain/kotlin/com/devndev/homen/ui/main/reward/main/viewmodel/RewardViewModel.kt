package com.devndev.homen.ui.main.reward.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.reward.GetRewardsUseCase
import kotlinx.coroutines.launch

class RewardViewModel(
    private val getRewardsUseCase: GetRewardsUseCase
) : BaseViewModel<RewardContract.Event, RewardContract.State, RewardContract.Effect>() {
    override fun setInitialState() = RewardContract.State()

    override fun handleEvents(event: RewardContract.Event) {
        when (event) {
            RewardContract.Event.OnInit -> {
                getRewards()
            }
        }
    }

    private fun getRewards() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }

            val result = getRewardsUseCase()

            when (result) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            rewards = result.data.rewards,
                            myPoint = result.data.myPoint,
                            claimableCount = result.data.claimableCount,
                            inProgressCount = result.data.inProgressCount,
                            claimedCount = result.data.claimedCount
                        )
                    }
                }

                else -> {

                }
            }

            setState { copy(mainIsLoading = false) }
        }
    }
}
package com.devndev.homen.ui.main.reward.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.reward.GetRewardDetailUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class RewardDetailViewModel(
    private val getRewardDetailUseCase: GetRewardDetailUseCase,
    private val getMyInfoUseCase: GetMyInfoUseCase
) : BaseViewModel<RewardDetailContract.Event, RewardDetailContract.State, RewardDetailContract.Effect>() {

    override fun setInitialState() = RewardDetailContract.State()

    override fun handleEvents(event: RewardDetailContract.Event) {
        when (event) {
            is RewardDetailContract.Event.OnInit -> {
                getRewardDetail(event.rewardId)
            }
            RewardDetailContract.Event.OnBackClick -> {
                setEffect { RewardDetailContract.Effect.NavigateToBack }
            }

            is RewardDetailContract.Event.OnNavToEditClick -> {
                setEffect { RewardDetailContract.Effect.NavigateToEditReward(event.rewardId, event.reward, event.point, event.isEdit) }
            }
        }
    }

    private fun getRewardDetail(rewardId: Int) {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }
            val detailResult = getRewardDetailUseCase(rewardId)
            val myInfoResult = getMyInfoUseCase()

            if (detailResult is ApiResult.Success && myInfoResult is ApiResult.Success) {
                val detail = detailResult.data
                val myName = myInfoResult.data.name
                val myProgress = detail.memberProgress.find { it.name == myName }
                setState {
                    copy(
                        mainIsLoading = false,
                        rewardDetail = detail,
                        myProgress = myProgress
                    )
                }
            } else {
                setState { copy(mainIsLoading = false) }
            }
        }
    }
}

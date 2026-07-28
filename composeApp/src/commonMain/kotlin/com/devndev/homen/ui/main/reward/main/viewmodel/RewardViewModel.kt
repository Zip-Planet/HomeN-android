package com.devndev.homen.ui.main.reward.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.reward.Reward
import com.devndev.homen.core.domain.model.reward.RewardCreator
import com.devndev.homen.core.domain.model.reward.RewardStatus
import com.devndev.homen.core.domain.usecase.reward.GetRewardsUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import kotlinx.coroutines.launch

class RewardViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getRewardsUseCase: GetRewardsUseCase
) : BaseViewModel<RewardContract.Event, RewardContract.State, RewardContract.Effect>() {
    override fun setInitialState() = RewardContract.State()

    override fun handleEvents(event: RewardContract.Event) {
        when (event) {
            RewardContract.Event.OnInit -> {
                getMyInfo()
                getRewards()
            }

            RewardContract.Event.OnCreateRewardClick -> {
                setEffect { RewardContract.Effect.NavigateToRewardEdit(null, null, null, false) }
            }
        }
    }

    private fun getRewards() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }

            // 가짜 데이터 10개 생성 (createdBy non-null 적용)
            val dummyRewards = List(10) { index ->
                Reward(
                    id = index,
                    name = when (index % 3) {
                        0 -> "집안일 일주일 제외"
                        1 -> "저녁 N빵 면제권"
                        else -> "스벅 기프티콘"
                    },
                    goalPoint = (index + 1) * 1000,
                    status = when {
                        index < 3 -> RewardStatus.CLAIMABLE
                        index < 7 -> RewardStatus.IN_PROGRESS
                        else -> RewardStatus.CLAIMED
                    },
                    remainingPoint = if (index < 3) 0 else 500,
                    createdBy = RewardCreator(
                        uid = "test-uid",
                        name = "사용자$index",
                        profileImage = (index % 5) + 1
                    ),
                    claim = null,
                    createdAt = "2024-05-20T12:00:00Z"
                )
            }

            setState {
                copy(
                    rewards = dummyRewards,
                    myPoint = 5000,
                    claimableCount = 3,
                    inProgressCount = 4,
                    claimedCount = 3
                )
            }

            // API 호출 주석 처리 (테스트용)
            /*
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
                else -> {}
            }
            */

            setState { copy(mainIsLoading = false) }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            val result = getMyInfoUseCase()

            when (result) {
                is ApiResult.Success -> {
                    setState { copy(profileImage = result.data.profileImage ?: 1) }
                }

                else -> {

                }
            }
        }
    }
}

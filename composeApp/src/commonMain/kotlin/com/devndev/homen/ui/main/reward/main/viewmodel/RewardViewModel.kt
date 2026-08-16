package com.devndev.homen.ui.main.reward.main.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.reward.DeleteRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.GetRewardsUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract.Effect.NavigateToRewardEdit
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract.Effect.ShowDeleteSnackBar
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RewardViewModel(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val getRewardsUseCase: GetRewardsUseCase,
    private val deleteRewardUseCase: DeleteRewardUseCase
) : BaseViewModel<RewardContract.Event, RewardContract.State, RewardContract.Effect>() {

    private val pendingDeleteJobs = mutableMapOf<Int, Job>()

    override fun setInitialState() = RewardContract.State()

    override fun handleEvents(event: RewardContract.Event) {
        when (event) {
            RewardContract.Event.OnInit -> {
                getMyInfo()
                getRewards()
            }

            RewardContract.Event.OnDispose -> {
                flushPendingDeletes()
            }

            RewardContract.Event.OnCreateRewardClick -> {
                setEffect { NavigateToRewardEdit(null, null, null, false) }
            }

            is RewardContract.Event.OnEditClick -> {
                setEffect {
                    NavigateToRewardEdit(
                        event.reward.id,
                        event.reward.name,
                        event.reward.goalPoint.toString(),
                        true
                    )
                }
            }

            is RewardContract.Event.OnDeleteClick -> {
                val index = viewState.value.rewards.indexOfFirst { it.id == event.id }
                if (index != -1) {
                    val reward = viewState.value.rewards[index]
                    val updatedList = viewState.value.rewards.toMutableList().apply {
                        removeAt(index)
                    }
                    setState { copy(rewards = updatedList) }
                    startPendingDelete(event.id)
                    setEffect { ShowDeleteSnackBar(reward, index) }
                }
            }

            is RewardContract.Event.OnUndoDelete -> {
                val id = event.reward.id
                pendingDeleteJobs[id]?.cancel()
                pendingDeleteJobs.remove(id)

                val currentList = viewState.value.rewards.toMutableList()
                if (event.index in 0..currentList.size) {
                    currentList.add(event.index, event.reward)
                } else {
                    currentList.add(event.reward)
                }
                setState { copy(rewards = currentList) }
            }

            is RewardContract.Event.OnDeleteConfirm -> {
                pendingDeleteJobs[event.id]?.cancel()
                pendingDeleteJobs.remove(event.id)
                deleteReward(event.id)
            }

            is RewardContract.Event.OnRewardClick -> {
                setEffect { RewardContract.Effect.NavigateToRewardDetail(event.id) }
            }
        }
    }

    private fun startPendingDelete(id: Int) {
        pendingDeleteJobs[id]?.cancel()
        val job = viewModelScope.launch {
            try {
                delay(5000)
                deleteReward(id)
                pendingDeleteJobs.remove(id)
            } catch (e: Exception) {
                // Cancelled
            }
        }
        pendingDeleteJobs[id] = job
    }

    private fun flushPendingDeletes() {
        if (pendingDeleteJobs.isEmpty()) return
        pendingDeleteJobs.forEach { (id, job) ->
            job.cancel()
            viewModelScope.launch(NonCancellable) {
                deleteRewardUseCase(id)
            }
        }
        pendingDeleteJobs.clear()
    }

    override fun onCleared() {
        flushPendingDeletes()
        super.onCleared()
    }

    private fun getRewards() {
        viewModelScope.launch {
            setState { copy(mainIsLoading = true) }

            val result = getRewardsUseCase()
            when (result) {
                is ApiResult.Success -> {
                    val filteredRewards = result.data.rewards.filter { it.id !in pendingDeleteJobs.keys }
                    setState {
                        copy(
                            rewards = filteredRewards,
                            myPoint = result.data.myPoint,
                            claimableCount = result.data.claimableCount,
                            inProgressCount = result.data.inProgressCount,
                            claimedCount = result.data.claimedCount
                        )
                    }
                }
                else -> {}
            }

            setState { copy(mainIsLoading = false) }
        }
    }

    private fun deleteReward(id: Int) {
        viewModelScope.launch(NonCancellable) {
            val result = deleteRewardUseCase(id)
            if (result !is ApiResult.Success) {
                getRewards()
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            val result = getMyInfoUseCase()

            when (result) {
                is ApiResult.Success -> {
                    setState { copy(profileImage = result.data.profileImage ?: 1) }
                }
                else -> {}
            }
        }
    }
}

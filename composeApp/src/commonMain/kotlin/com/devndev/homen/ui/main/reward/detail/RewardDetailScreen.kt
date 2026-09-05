package com.devndev.homen.ui.main.reward.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.reward.detail.viewmodel.RewardDetailContract
import com.devndev.homen.ui.main.reward.detail.viewmodel.RewardDetailViewModel
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.reward_claim_toast
import homen.composeapp.generated.resources.reward_detail_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RewardDetailScreen(
    viewModel: RewardDetailViewModel = koinViewModel(),
    rewardId: Int,
    onEditClick: (Int?, String?, String?, Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RewardDetailContract.Effect.NavigateToBack -> {
                    onBackClick()
                }

                is RewardDetailContract.Effect.NavigateToEditReward -> {
                    onEditClick(effect.rewardId, effect.reward, effect.point, effect.isEdit)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(RewardDetailContract.Event.OnInit(rewardId))
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.reward_detail_title),
                onBackClick = {
                    viewModel.setEvent(RewardDetailContract.Event.OnBackClick)
                }
            )
        },
        mainIsLoading = uiState.mainIsLoading
    ) {
        val toastMsg = stringResource(Res.string.reward_claim_toast).replace("s", uiState.rewardDetail?.goalPoint.toString() ?: "")

        RewardDetailContent(
            uiState = uiState,
            onEditClick = {
                viewModel.setEvent(
                    RewardDetailContract.Event.OnNavToEditClick(
                        rewardId,
                        uiState.rewardDetail?.name,
                        uiState.rewardDetail?.goalPoint.toString(),
                        true
                    )
                )
            },
            onGetRewardClick = {
                viewModel.setEvent(
                    RewardDetailContract.Event.OnClaimRewardClick(rewardId = rewardId, toastMsg = toastMsg)
                )
            }
        )
    }
}
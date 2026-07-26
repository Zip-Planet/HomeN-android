package com.devndev.homen.ui.main.reward.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.reward
import homen.composeapp.generated.resources.reward_empty_btn
import homen.composeapp.generated.resources.reward_empty_msg
import homen.composeapp.generated.resources.reward_empty_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RewardScreen(
    viewModel: RewardViewModel = koinViewModel(),
    onNavToEditReward: (Int?, String?, String?, Boolean) -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is RewardContract.Effect.NavigateToRewardEdit -> {
                    onNavToEditReward(effect.rewardId, effect.reward, effect.point, effect.isEdit)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(RewardContract.Event.OnInit)
    }

    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.reward)
            )
        },
        isLoading = uiState.isLoading,
        mainIsLoading = uiState.mainIsLoading
    ) {
        if (uiState.isRewardExist) {

        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                    .padding(top = 27.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(horizontal = 15.dp, vertical = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.present_icon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(Res.string.reward_empty_title),
                        style = HomeNTheme.typography.suitExtraBold,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = stringResource(Res.string.reward_empty_msg),
                    style = HomeNTheme.typography.suitRegular,
                    color = Color.Black,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                HomeNButton(
                    text = stringResource(Res.string.reward_empty_btn),
                    onClick = {
                        viewModel.setEvent(RewardContract.Event.OnCreateRewardClick)
                    }
                )
            }
        }
    }
}
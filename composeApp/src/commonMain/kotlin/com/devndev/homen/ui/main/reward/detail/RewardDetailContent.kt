package com.devndev.homen.ui.main.reward.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.reward.RewardStatus
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.main.reward.detail.viewmodel.RewardDetailContract
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.fire_icon
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.reward_claimed_btn
import homen.composeapp.generated.resources.reward_edit_btn
import homen.composeapp.generated.resources.reward_edit_title
import homen.composeapp.generated.resources.reward_empty_btn
import homen.composeapp.generated.resources.reward_get_btn
import homen.composeapp.generated.resources.reward_goal_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RewardDetailContent(
    uiState: RewardDetailContract.State,
    onEditClick: () -> Unit,
    onGetRewardClick: () -> Unit
) {
    val rewardStatus = uiState.rewardDetail?.status
    val btnText = when (rewardStatus) {
        RewardStatus.CLAIMED -> stringResource(Res.string.reward_claimed_btn)
        RewardStatus.CLAIMABLE -> stringResource(Res.string.reward_get_btn)
        RewardStatus.IN_PROGRESS -> stringResource(Res.string.reward_edit_btn)
        else -> ""
    }

    Column(
        modifier = Modifier
            .padding(top = 42.dp)
            .fillMaxSize()
            .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            .padding(bottom = HomeNTheme.dimensions.bottomPadding)
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
                text = uiState.rewardDetail?.name ?: "",
                style = HomeNTheme.typography.suitBold,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // TODO::SHKIM API Response 유저정보 누락으로 추가 시 화면 수정 필요
        when (rewardStatus) {
            RewardStatus.CLAIMED -> {

            }
            RewardStatus.CLAIMABLE,
            RewardStatus.IN_PROGRESS -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(
                            horizontal = 15.dp,
                            vertical = 20.dp
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.fire_icon),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color.Black
                        )

                        Text(
                            text = stringResource(Res.string.reward_goal_title),
                            style = HomeNTheme.typography.suitExtraBold,
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "${uiState.rewardDetail.goalPoint}P",
                            style = HomeNTheme.typography.suitRegular,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                }

            }
            else -> { }
        }


        Spacer(modifier = Modifier.weight(1f))

        HomeNButton(
            text = btnText,
            onClick = {
                when (rewardStatus) {
                    RewardStatus.CLAIMABLE -> {
                        onGetRewardClick()
                    }
                    RewardStatus.IN_PROGRESS -> {
                        onEditClick()
                    }
                    else -> {

                    }
                }
            },
            enabled = rewardStatus != RewardStatus.CLAIMED
        )
    }
}

@Preview
@Composable
fun RewardDetailContentPreview() {
    RewardDetailContent(
        uiState = RewardDetailContract.State(),
        onEditClick = {},
        onGetRewardClick = {}
    )
}
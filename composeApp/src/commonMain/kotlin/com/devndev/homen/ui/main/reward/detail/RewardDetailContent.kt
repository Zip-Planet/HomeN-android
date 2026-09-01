package com.devndev.homen.ui.main.reward.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.reward.MemberProgress
import com.devndev.homen.core.domain.model.reward.RewardStatus
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNProgressBar
import com.devndev.homen.ui.main.reward.detail.viewmodel.RewardDetailContract
import com.devndev.homen.ui.theme.Blue4736FC
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Gray8E8E8E
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.diamond_icon
import homen.composeapp.generated.resources.fire_icon
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.reward_achievement_rate_label
import homen.composeapp.generated.resources.reward_available_point_label
import homen.composeapp.generated.resources.reward_claimed_btn
import homen.composeapp.generated.resources.reward_detail_progress_claimable_message
import homen.composeapp.generated.resources.reward_detail_progress_in_progress_message
import homen.composeapp.generated.resources.reward_edit_btn
import homen.composeapp.generated.resources.reward_get_btn
import homen.composeapp.generated.resources.reward_goal_title
import homen.composeapp.generated.resources.reward_member_title
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
                    text = "${uiState.rewardDetail?.goalPoint}P",
                    style = HomeNTheme.typography.suitRegular,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (rewardStatus) {
                RewardStatus.CLAIMED -> {
                    //TODO::SHKIM 서버 response 수정 시 작업 -> 보상 수령 멤버 및 시간 필요
                }

                RewardStatus.CLAIMABLE,
                RewardStatus.IN_PROGRESS -> {
                    val progressText = if (rewardStatus == RewardStatus.CLAIMABLE) {
                        stringResource(Res.string.reward_detail_progress_claimable_message)
                    } else {
                        stringResource(Res.string.reward_detail_progress_in_progress_message)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                AvatarType.fromId(
                                    uiState.myProgress?.profileImage ?: 1
                                ).resource
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = progressText.replace(
                                "s",
                                uiState.myProgress?.achievementRate?.toString() ?: ""
                            ),
                            style = HomeNTheme.typography.suitRegular,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(11.dp)
                    ) {
                        HomeNProgressBar(
                            progress = (uiState.myProgress?.achievementRate ?: 0) / 100f,
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "${uiState.myProgress?.point}P",
                            style = HomeNTheme.typography.suitRegular,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }

                else -> {}
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (rewardStatus) {
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
                            painter = painterResource(Res.drawable.diamond_icon),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color.Black
                        )

                        Text(
                            text = stringResource(Res.string.reward_member_title),
                            style = HomeNTheme.typography.suitExtraBold,
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(13.dp))

                    uiState.rewardDetail?.memberProgress?.sortedBy { it.rank }?.let { members ->
                        members.forEachIndexed { index, memberProgress ->
                            RewardMemberItem(
                                member = memberProgress
                            )

                            if (index < members.lastIndex) {
                                Spacer(
                                    modifier = Modifier.height(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            else -> {

            }
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

@Composable
fun RewardMemberItem(
    member: MemberProgress
) {
    val backgroundColor = if (member.rank == 1) Blue4736FC else ButtonGray
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .width(30.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            if (member.rank == 1) {
                Text(
                    text = "${member.rank}등",
                    style = HomeNTheme.typography.suitSemiBold,
                    color = Color.White,
                    fontSize = 12.sp
                )
            } else {
                Text(
                    text = "${member.rank}등",
                    style = HomeNTheme.typography.suitRegular,
                    color = Color.Black,
                    fontSize = 10.sp
                )
            }

        }

        Spacer(modifier = Modifier.width(7.dp))

        Image(
            painter = painterResource(
                AvatarType.fromId(
                    member.profileImage
                ).resource
            ),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )

        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            modifier = Modifier.weight(1f),
            text = member.name,
            style = HomeNTheme.typography.suitRegular,
            color = Color.Black,
            fontSize = 14.sp
        )

        Column(
            modifier = Modifier.height(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.reward_available_point_label),
                style = HomeNTheme.typography.suitRegular,
                color = Gray8E8E8E,
                fontSize = 8.sp
            )

            Text(
                text = "${member.point}P",
                style = HomeNTheme.typography.suitBold,
                color = Color.Black,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column(
            modifier = Modifier.height(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.reward_achievement_rate_label),
                style = HomeNTheme.typography.suitRegular,
                color = Gray8E8E8E,
                fontSize = 8.sp
            )

            Text(
                text = "${member.achievementRate}%",
                style = HomeNTheme.typography.suitBold,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
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

@Preview
@Composable
fun RewardMemberItemPreview() {
    RewardMemberItem(
        MemberProgress(
            profileImage = 1,
            point = 100,
            achievementRate = 100,
            name = "김수환",
            rank = 1
        )
    )
}

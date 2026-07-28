package com.devndev.homen.ui.main.reward.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.reward.Reward
import com.devndev.homen.core.domain.model.reward.RewardStatus
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardContract
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue4
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.Gray8E8E8E
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.edit_alt_icon
import homen.composeapp.generated.resources.floating_btn_icon
import homen.composeapp.generated.resources.heart_icon
import homen.composeapp.generated.resources.menu_dot_icon
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.reward
import homen.composeapp.generated.resources.reward_complete_title
import homen.composeapp.generated.resources.reward_my_complete_label
import homen.composeapp.generated.resources.reward_my_point_label
import homen.composeapp.generated.resources.reward_my_possible_label
import homen.composeapp.generated.resources.reward_my_progress_label
import homen.composeapp.generated.resources.reward_point_complete_badge_message
import homen.composeapp.generated.resources.reward_point_progress_badge_message
import homen.composeapp.generated.resources.reward_point_progress_badge_remain_message
import homen.composeapp.generated.resources.trash_alt_icon
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RewardExistScreen(
    uiState: RewardContract.State,
    onAddButtonClick: () -> Unit = {},
    onEditClick: (Reward) -> Unit = {},
    onDeleteClick: (Int) -> Unit = {},
    onRewardClick: (Reward) -> Unit = {}
) {
    var expandedRewardId by remember { mutableStateOf<Int?>(null) }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { expandedRewardId = null })
            }
    ) {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .padding(top = 27.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // My Point Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(AvatarType.fromId(uiState.profileImage).resource),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Text(
                        text = stringResource(Res.string.reward_my_point_label),
                        style = HomeNTheme.typography.suitSemiBold,
                        color = Color.Black,
                        fontSize = 10.sp
                    )

                    Text(
                        text = "${uiState.myPoint}P",
                        style = HomeNTheme.typography.suitExtraBold,
                        color = Color.Black,
                        fontSize = 22.sp
                    )
                }

                MyRewardStatusItem(
                    text = stringResource(Res.string.reward_my_possible_label),
                    status = uiState.claimableCount
                )

                Spacer(modifier = Modifier.width(8.dp))

                MyRewardStatusItem(
                    text = stringResource(Res.string.reward_my_progress_label),
                    status = uiState.inProgressCount
                )

                Spacer(modifier = Modifier.width(8.dp))

                MyRewardStatusItem(
                    text = stringResource(Res.string.reward_my_complete_label),
                    status = uiState.claimedCount
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

            // Reward Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = screenHeight)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(Color.White)
                    .padding(
                        start = HomeNTheme.dimensions.horizontalPadding,
                        end = HomeNTheme.dimensions.horizontalPadding,
                        top = 30.dp,
                        bottom = 40.dp
                    )
            ) {
                // In-Progress / Claimable Rewards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.present_icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = stringResource(Res.string.reward),
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                uiState.rewards.filter { it.status != RewardStatus.CLAIMED }.forEach { reward ->
                    RewardListItem(
                        reward = reward,
                        isExpanded = expandedRewardId == reward.id,
                        onMenuClick = {
                            expandedRewardId =
                                if (expandedRewardId == reward.id) null else reward.id
                        },
                        onEditClick = { onEditClick(reward) },
                        onDeleteClick = { onDeleteClick(reward.id) },
                        onRewardClick = { onRewardClick(reward) }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Completed Rewards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.present_icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = stringResource(Res.string.reward_complete_title),
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                uiState.rewards.filter { it.status == RewardStatus.CLAIMED }.forEach { reward ->
                    RewardCompleteListItem(
                        reward
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

        // Floating Action Button
        Icon(
            painter = painterResource(Res.drawable.floating_btn_icon),
            contentDescription = "add reward icon",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 36.dp, bottom = 55.dp)
                .size(51.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onAddButtonClick()
                }
        )
    }
}

@Composable
fun MyRewardStatusItem(
    text: String,
    status: Int
) {
    Column(
        modifier = Modifier
            .width(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            style = HomeNTheme.typography.suitRegular,
            color = Gray8E8E8E,
            fontSize = 10.sp
        )

        Text(
            text = status.toString(),
            style = HomeNTheme.typography.suitRegular,
            color = Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun RewardListItem(
    reward: Reward,
    isExpanded: Boolean,
    onMenuClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onRewardClick: () -> Unit
) {
    val heartColors = listOf(
        Color(0xFFFF5E5E), // Red
        Color(0xFFFF8E1F), // Orange
        Color(0xFFFFD644), // Yellow
        Color(0xFF49D189), // Green
        Color(0xFF387FFF), // Blue
        Color(0xFF8B73FF)  // Purple
    )
    val tintColor = heartColors[reward.id % heartColors.size]

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onRewardClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.heart_icon),
                contentDescription = null,
                modifier = Modifier.size(13.dp),
                tint = tintColor
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = reward.name,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )

            when (reward.status) {
                RewardStatus.IN_PROGRESS -> {
                    Box(
                        modifier = Modifier
                            .height(17.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(ButtonGray)
                            .padding(horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = stringResource(Res.string.reward_point_progress_badge_message).replace(
                                    "s",
                                    reward.goalPoint.toString()
                                ),
                                style = HomeNTheme.typography.suitBold,
                                fontSize = 10.sp,
                                color = Color.Black
                            )

                            Dot(
                                width = 8,
                                dotSize = 2,
                                height = 2
                            )

                            Text(
                                text = stringResource(Res.string.reward_point_progress_badge_remain_message).replace(
                                    "s",
                                    reward.remainingPoint.toString()
                                ),
                                style = HomeNTheme.typography.suitRegular,
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                RewardStatus.CLAIMABLE -> {
                    Box(
                        modifier = Modifier
                            .height(17.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(Blue4)
                            .padding(horizontal = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.reward_point_complete_badge_message).replace(
                                "s",
                                reward.goalPoint.toString()
                            ),
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    }
                }

                else -> {

                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AnimatedVisibility(
                visible = isExpanded,
                enter = slideInHorizontally(
                    initialOffsetX = { it / 2 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) + fadeIn(),
                exit = slideOutHorizontally(
                    targetOffsetX = { it / 2 },
                    animationSpec = tween(150)
                ) + fadeOut()
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(
                        painter = painterResource(Res.drawable.edit_alt_icon),
                        contentDescription = "edit reward",
                        modifier = Modifier.size(20.dp)
                            .clickable { onEditClick() }
                    )
                    Icon(
                        painter = painterResource(Res.drawable.trash_alt_icon),
                        contentDescription = "delete reward",
                        modifier = Modifier.size(20.dp)
                            .clickable { onDeleteClick() }
                    )
                }
            }

            Icon(
                painter = painterResource(Res.drawable.menu_dot_icon),
                contentDescription = "menu",
                modifier = Modifier.size(20.dp)
                    .background(color = Color.White)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onMenuClick()
                    }
            )
        }
    }
}

@Composable
fun RewardCompleteListItem(
    reward: Reward
) {
    val heartColors = listOf(
        Color(0xFF8B73FF),  // Purple
        Color(0xFFFF5E5E), // Red
        Color(0xFF387FFF), // Blue
        Color(0xFFFFD644), // Yellow
        Color(0xFF49D189), // Green
        Color(0xFFFF8E1F), // Orange
    )
    val tintColor = heartColors[reward.id % heartColors.size]

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.heart_icon),
                contentDescription = null,
                modifier = Modifier.size(13.dp),
                tint = tintColor
            )
        }

        Spacer(modifier = Modifier.width(7.dp))

        Text(
            text = reward.name,
            style = HomeNTheme.typography.suitBold,
            color = Color.Black,
            fontSize = 13.sp
        )

        Dot(
            width = 10,
            dotSize = 2,
            height = 2
        )

        Text(
            text = reward.createdBy?.name ?: "",
            style = HomeNTheme.typography.suitBold,
            color = Color.Black,
            fontSize = 13.sp
        )
    }
}

@Preview
@Composable
fun RewardExistScreenPreview() {
    HomeNTheme {
        RewardExistScreen(
            uiState = RewardContract.State()
        )
    }
}

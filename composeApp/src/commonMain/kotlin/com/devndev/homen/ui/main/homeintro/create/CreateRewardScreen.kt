package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNTooltip
import com.devndev.homen.ui.component.HomeNUnderlineTextField
import com.devndev.homen.ui.component.TooltipButton
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.ThousandSeparatorTransformation
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home_create_reward_complete_btn
import homen.composeapp.generated.resources.home_create_reward_msg
import homen.composeapp.generated.resources.home_create_reward_skip_btn
import homen.composeapp.generated.resources.home_create_reward_title
import homen.composeapp.generated.resources.home_create_reward_tooltip_msg
import homen.composeapp.generated.resources.home_create_reward_tooltip_title
import homen.composeapp.generated.resources.plus_icon
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.reward_card_title
import homen.composeapp.generated.resources.reward_name_hint1
import homen.composeapp.generated.resources.reward_name_hint2
import homen.composeapp.generated.resources.reward_name_hint3
import homen.composeapp.generated.resources.reward_name_label
import homen.composeapp.generated.resources.reward_point_hint1
import homen.composeapp.generated.resources.reward_point_hint2
import homen.composeapp.generated.resources.reward_point_hint3
import homen.composeapp.generated.resources.reward_point_label
import homen.composeapp.generated.resources.x_btn
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateRewardScreen(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CreateHomeContract.Effect.NavToNext -> onCompleteClick()
                CreateHomeContract.Effect.PopBackStack -> onBackClick()
                else -> {}
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(true))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = HomeNTheme.dimensions.horizontalPadding,
                end = HomeNTheme.dimensions.horizontalPadding,
                top = 52.dp,
                bottom = HomeNTheme.dimensions.bottomPadding,
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.home_create_reward_title),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = stringResource(Res.string.home_create_reward_msg),
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                lineHeight = 1.6.em,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                itemsIndexed(uiState.rewards) { index, reward ->
                    RewardInputCard(
                        index = index,
                        name = reward.name,
                        point = reward.targetPoint,
                        onNameChanged = { viewModel.setEvent(CreateHomeContract.Event.OnRewardNameChanged(index, it)) },
                        onPointChanged = { viewModel.setEvent(CreateHomeContract.Event.OnRewardPointChanged(index, it)) },
                        onRemoveClick = { viewModel.setEvent(CreateHomeContract.Event.OnRemoveRewardClick(index)) },
                        showRemoveButton = uiState.rewards.size > 1
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(Color.Black, RoundedCornerShape(10.dp))
                            .clickable { viewModel.setEvent(CreateHomeContract.Event.OnAddRewardClick) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.plus_icon),
                            contentDescription = "add reward",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeNButton(
                    text = stringResource(Res.string.home_create_reward_skip_btn),
                    onClick = { viewModel.setEvent(CreateHomeContract.Event.OnSkipClick) },
                    modifier = Modifier.weight(1f),
                )

                HomeNButton(
                    text = stringResource(Res.string.home_create_reward_complete_btn),
                    onClick = { viewModel.setEvent(CreateHomeContract.Event.OnCompleteClick) },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.rewards.any { it.name.isNotEmpty() && it.targetPoint.isNotEmpty() }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 1.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TooltipButton {
                viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(!uiState.showTooltip))
            }

            if (uiState.showTooltip) {
                HomeNTooltip(
                    title = stringResource(Res.string.home_create_reward_tooltip_title),
                    messages = listOf(stringResource(Res.string.home_create_reward_tooltip_msg)),
                    onCloseClick = {
                        viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(false))
                    },
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures { }
                    }
                )
            }
        }
    }

    if (uiState.showTooltip) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        viewModel.setEvent(CreateHomeContract.Event.OnTooltipToggle(false))
                    }
                }
        )
    }
}

@Composable
fun RewardInputCard(
    index: Int,
    name: String,
    point: String,
    onNameChanged: (String) -> Unit,
    onPointChanged: (String) -> Unit,
    onRemoveClick: () -> Unit,
    showRemoveButton: Boolean
) {
    val nameHint = when (index % 3) {
        0 -> stringResource(Res.string.reward_name_hint1)
        1 -> stringResource(Res.string.reward_name_hint2)
        else -> stringResource(Res.string.reward_name_hint3)
    }
    val pointHint = when (index % 3) {
        0 -> stringResource(Res.string.reward_point_hint1)
        1 -> stringResource(Res.string.reward_point_hint2)
        else -> stringResource(Res.string.reward_point_hint3)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.present_icon),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(Res.string.reward_card_title).replace("n", (index + 1).toString()),
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }

            if (showRemoveButton) {
                Icon(
                    painter = painterResource(Res.drawable.x_btn),
                    contentDescription = "remove",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onRemoveClick() },
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(Res.string.reward_name_label),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        HomeNUnderlineTextField(
            value = name,
            onValueChange = onNameChanged,
            hint = nameHint,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = stringResource(Res.string.reward_point_label),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        HomeNUnderlineTextField(
            value = point,
            onValueChange = onPointChanged,
            hint = pointHint,
            modifier = Modifier.padding(top = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            regex = Regex("^[1-9][0-9]*$|^$"),
            visualTransformation = ThousandSeparatorTransformation
        )
    }
}

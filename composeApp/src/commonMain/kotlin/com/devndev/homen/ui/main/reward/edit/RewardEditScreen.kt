package com.devndev.homen.ui.main.reward.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNLongTextField
import com.devndev.homen.ui.component.HomeNNumberTextField
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.reward.edit.viewmodel.RewardEditContract
import com.devndev.homen.ui.main.reward.edit.viewmodel.RewardEditViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.reward_create_title
import homen.composeapp.generated.resources.reward_edit_name_title
import homen.composeapp.generated.resources.reward_edit_point_title
import homen.composeapp.generated.resources.reward_edit_title
import homen.composeapp.generated.resources.reward_name_hint1
import homen.composeapp.generated.resources.reward_point_hint1
import homen.composeapp.generated.resources.reward_save_button
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RewardEditScreen(
    viewModel: RewardEditViewModel = koinViewModel(),
    rewardId: Int?,
    reward: String?,
    point: String?,
    isEdit: Boolean,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RewardEditContract.Effect.NavigateToBack -> {
                    onBackClick()
                }
            }
        }
    }

    val title = if (isEdit) {
        stringResource(Res.string.reward_edit_title)
    } else {
        stringResource(Res.string.reward_create_title)
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = title,
                onBackClick = onBackClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 42.dp)
                .fillMaxSize()
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
                .padding(bottom = HomeNTheme.dimensions.bottomPadding)
        ) {
            Text(
                text = stringResource(Res.string.reward_edit_name_title),
                style = HomeNTheme.typography.suitBold,
                color = Color.Black,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeNLongTextField(
                value = uiState.reward,
                onValueChange = {
                    viewModel.setEvent(RewardEditContract.Event.OnRewardChange(it))
                },
                hint = stringResource(Res.string.reward_name_hint1),
                maxChar = 20,
                enabled = true,
                regex = null
            )

            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = stringResource(Res.string.reward_edit_point_title),
                style = HomeNTheme.typography.suitBold,
                color = Color.Black,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeNNumberTextField(
                value = uiState.point,
                onValueChange = {
                    viewModel.setEvent(RewardEditContract.Event.OnPointChange(it))
                },
                hint = stringResource(Res.string.reward_point_hint1),
                enabled = true,
            )

            Spacer(modifier = Modifier.weight(1f))

            HomeNButton(
                text = stringResource(Res.string.reward_save_button),
                onClick = {
                },
                enabled = uiState.isSaveButtonEnable
            )
        }
    }
}
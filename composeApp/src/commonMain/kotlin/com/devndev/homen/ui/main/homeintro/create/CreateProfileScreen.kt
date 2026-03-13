package com.devndev.homen.ui.main.homeintro.create

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNUnderlineTextField
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeContract
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home1_small_icon
import homen.composeapp.generated.resources.home2_small_icon
import homen.composeapp.generated.resources.home3_small_icon
import homen.composeapp.generated.resources.home_create_profile_title
import homen.composeapp.generated.resources.nickname_hint
import homen.composeapp.generated.resources.select_avatar_msg
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateProfileScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: CreateHomeViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is CreateHomeContract.Effect.NavToNext -> onNextClick()
                is CreateHomeContract.Effect.PopBackStack -> onBackClick()
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .padding(
                start = HomeNTheme.dimensions.horizontalPadding,
                end = HomeNTheme.dimensions.horizontalPadding,
                bottom = HomeNTheme.dimensions.bottomPadding,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = stringResource(Res.string.home_create_profile_title),
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                lineHeight = 1.6.em,
                letterSpacing = (-0.022).em,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            HomeNUnderlineTextField(
                value = uiState.homeName,
                onValueChange = { viewModel.setEvent(CreateHomeContract.Event.OnHomeNameChanged(it)) },
                hint = stringResource(Res.string.nickname_hint),
                maxChar = 10
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(Res.string.select_avatar_msg),
                style = HomeNTheme.typography.suitBold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                repeat(3) { index ->
                    AvatarItem(
                        index = index,
                        isSelected = uiState.avatarId == index,
                        onClick = {
                            viewModel.setEvent(
                                CreateHomeContract.Event.OnAvatarSelected(
                                    index
                                )
                            )
                        }
                    )
                }
            }
        }
        HomeNButton(
            text = "다음",
            onClick = { viewModel.setEvent(CreateHomeContract.Event.OnNextClick) },
            enabled = uiState.homeName.isNotEmpty() && uiState.avatarId != null,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AvatarItem(
    index: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val icon = when (index) {
        0 -> Res.drawable.home1_small_icon
        1 -> Res.drawable.home2_small_icon
        2 -> Res.drawable.home3_small_icon
        else -> Res.drawable.home1_small_icon
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(Color.White)
            .then(
                if (isSelected) Modifier.border(1.dp, Color.Black, CircleShape)
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = Color.Unspecified
        )
    }
}

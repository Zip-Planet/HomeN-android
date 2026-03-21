package com.devndev.homen.ui.intro.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
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
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNTextField
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.intro.register.viewmodel.RegisterContract
import com.devndev.homen.ui.intro.register.viewmodel.RegisterStep
import com.devndev.homen.ui.intro.register.viewmodel.RegisterViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.farmer_avatar
import homen.composeapp.generated.resources.guard_avatar
import homen.composeapp.generated.resources.hero_avatar
import homen.composeapp.generated.resources.next_button
import homen.composeapp.generated.resources.nickname_hint
import homen.composeapp.generated.resources.nickname_msg
import homen.composeapp.generated.resources.profile_setting_title
import homen.composeapp.generated.resources.select_avatar_msg
import homen.composeapp.generated.resources.wizard_avatar
import homen.composeapp.generated.resources.zombie_avatar
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onNavBack: () -> Unit,
    onNavToMain: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState
    val maxChar = 8
    val nicknameRegex = Regex("^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]*$")

    BackHandler {
        viewModel.setEvent(RegisterContract.Event.OnBackClick)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RegisterContract.Effect.NavigateToMain -> onNavToMain()
                RegisterContract.Effect.PopBackStack -> onNavBack()
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.profile_setting_title),
                onBackClick = { viewModel.setEvent(RegisterContract.Event.OnBackClick) },
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 52.dp),
            ) {
                Text(
                    text = stringResource(Res.string.nickname_msg),
                    style = HomeNTheme.typography.suitBold,
                    color = Color.Black,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                HomeNTextField(
                    value = uiState.nickname,
                    onValueChange = { viewModel.setEvent(RegisterContract.Event.OnNicknameChanged(it)) },
                    hint = stringResource(Res.string.nickname_hint),
                    maxChar = maxChar,
                    regex = nicknameRegex,
                    enabled = uiState.currentStep == RegisterStep.NICKNAME
                )
            }

            Spacer(modifier = Modifier.height(45.dp))

            AnimatedVisibility(
                visible = uiState.currentStep == RegisterStep.AVATAR,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut()
            ) {
                AvatarSelect(
                    selectedAvatarIndex = uiState.selectedAvatarIndex,
                    onAvatarSelected = { viewModel.setEvent(RegisterContract.Event.OnAvatarSelected(it)) }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            HomeNButton(
                text = stringResource(Res.string.next_button),
                onClick = { viewModel.setEvent(RegisterContract.Event.OnNextClick) },
                enabled = when (uiState.currentStep) {
                    RegisterStep.NICKNAME -> uiState.nickname.isNotEmpty()
                    RegisterStep.AVATAR -> uiState.selectedAvatarIndex != null
                }
            )
        }
    }
}

@Composable
fun AvatarSelect(
    selectedAvatarIndex: Int?,
    onAvatarSelected: (Int) -> Unit
) {
    val avatars = listOf(
        Res.drawable.chef_avatar,
        Res.drawable.wizard_avatar,
        Res.drawable.hero_avatar,
        Res.drawable.guard_avatar,
        Res.drawable.zombie_avatar,
        Res.drawable.farmer_avatar,
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.select_avatar_msg),
            style = HomeNTheme.typography.suitBold,
            fontSize = 18.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(30.dp))
        val chunkedAvatars = avatars.chunked(3)
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            chunkedAvatars.forEachIndexed { rowIndex, rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    rowItems.forEachIndexed { colIndex, avatar ->
                        val index = rowIndex * 3 + colIndex
                        val isSelected = selectedAvatarIndex == index
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .then(
                                    if (isSelected) Modifier.border(1.dp, Color.Black, CircleShape)
                                    else Modifier
                                )
                                .clickable { onAvatarSelected(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(avatar),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

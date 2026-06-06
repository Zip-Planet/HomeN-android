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
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.HomeNTextField
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.intro.register.viewmodel.RegisterContract
import com.devndev.homen.ui.intro.register.viewmodel.RegisterStep
import com.devndev.homen.ui.intro.register.viewmodel.RegisterViewModel
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.ui.theme.RedFF1E1E
import com.devndev.homen.ui.theme.RedFFCACA
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.next_button
import homen.composeapp.generated.resources.nickname_hint
import homen.composeapp.generated.resources.nickname_invalid_msg
import homen.composeapp.generated.resources.nickname_msg
import homen.composeapp.generated.resources.profile_setting_title
import homen.composeapp.generated.resources.select_avatar_msg
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onNavBack: () -> Unit,
    onNavToMain: (Boolean) -> Unit,
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
                RegisterContract.Effect.NavigateToMain -> onNavToMain(uiState.hasHome)
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
                    bottom = HomeNTheme.dimensions.bottomPadding,
                    top = HomeNTheme.dimensions.topPadding
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                    enabled = uiState.currentStep != RegisterStep.AVATAR,
                    backgroundColor = if (uiState.currentStep == RegisterStep.INVALID) RedFFCACA else Color.White
                )
            }

            Spacer(modifier = Modifier.height(45.dp))

            AnimatedVisibility(
                visible = uiState.currentStep == RegisterStep.AVATAR,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut()
            ) {
                AvatarSelect(
                    selectedAvatar = uiState.selectedAvatar,
                    onAvatarSelected = { viewModel.setEvent(RegisterContract.Event.OnAvatarSelected(it)) }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            if (uiState.currentStep == RegisterStep.INVALID) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.nickname_invalid_msg),
                        style = HomeNTheme.typography.suitLight,
                        color = RedFF1E1E,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            HomeNButton(
                text = stringResource(Res.string.next_button),
                onClick = { viewModel.setEvent(RegisterContract.Event.OnNextClick) },
                enabled = when (uiState.currentStep) {
                    RegisterStep.NICKNAME -> uiState.nickname.isNotEmpty()
                    RegisterStep.AVATAR -> uiState.selectedAvatar != null
                    RegisterStep.INVALID -> false
                }
            )
        }
    }
}

@Composable
fun AvatarSelect(
    selectedAvatar: AvatarType?,
    onAvatarSelected: (AvatarType) -> Unit
) {
    val avatars = AvatarType.entries

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
            chunkedAvatars.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    rowItems.forEach { avatarType ->
                        val isSelected = selectedAvatar == avatarType
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .then(
                                    if (isSelected) Modifier.border(1.dp, Color.Black, CircleShape)
                                    else Modifier
                                )
                                .clickable { onAvatarSelected(avatarType) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(avatarType.resource),
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

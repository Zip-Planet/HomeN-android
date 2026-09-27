package com.devndev.homen.ui.main.mypage.edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.devndev.homen.ui.main.mypage.edit.viewmodel.ProfileSettingContract
import com.devndev.homen.ui.main.mypage.edit.viewmodel.ProfileSettingViewModel
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.avatar_label
import homen.composeapp.generated.resources.edit_profile_title
import homen.composeapp.generated.resources.nickname_edit_subtitle
import homen.composeapp.generated.resources.nickname_hint
import homen.composeapp.generated.resources.nickname_label
import homen.composeapp.generated.resources.profile_saved_toast
import homen.composeapp.generated.resources.save_button
import kotlinx.coroutines.flow.collectLatest
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileSettingScreen(
    initialNickname: String,
    initialAvatarId: Int,
    onNavBack: () -> Unit,
    viewModel: ProfileSettingViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState
    val maxChar = 8
    val nicknameRegex = Regex("^[a-zA-Z가-힣ㄱ-ㅎㅏ-ㅣ]*$")
    val toastMsg = stringResource(Res.string.profile_saved_toast)

    BackHandler {
        viewModel.setEvent(ProfileSettingContract.Event.OnBackClick)
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(ProfileSettingContract.Event.OnInit(initialNickname, initialAvatarId))
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ProfileSettingContract.Effect.PopBackStack -> onNavBack()
                ProfileSettingContract.Effect.SaveSuccess -> {
                    showToast(
                        message = toastMsg,
                        backgroundColor = Color.Black.copy(alpha = 0.8f),
                        textColor = Color.White,
                        cornerRadius = 10,
                        duration = ToastDuration.Short
                    )
                    onNavBack()
                }
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.edit_profile_title),
                onBackClick = { viewModel.setEvent(ProfileSettingContract.Event.OnBackClick) },
            )
        },
        isLoading = uiState.isLoading
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
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(Res.string.nickname_label),
                        style = HomeNTheme.typography.suitBold,
                        color = Color.Black,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = stringResource(Res.string.nickname_edit_subtitle),
                        style = HomeNTheme.typography.suitRegular,
                        color = BottomGray,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    HomeNTextField(
                        value = uiState.nickname,
                        onValueChange = { viewModel.setEvent(ProfileSettingContract.Event.OnNicknameChanged(it)) },
                        hint = stringResource(Res.string.nickname_hint),
                        maxChar = maxChar,
                        regex = nicknameRegex,
                        backgroundColor = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(Res.string.avatar_label),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    AvatarSelect(
                        selectedAvatar = uiState.selectedAvatar,
                        onAvatarSelected = { viewModel.setEvent(ProfileSettingContract.Event.OnAvatarSelected(it)) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            HomeNButton(
                text = stringResource(Res.string.save_button),
                onClick = { viewModel.setEvent(ProfileSettingContract.Event.OnSaveClick) },
                enabled = uiState.isSaveEnabled
            )
        }
    }
}

@Composable
private fun AvatarSelect(
    selectedAvatar: AvatarType?,
    onAvatarSelected: (AvatarType) -> Unit
) {
    val avatars = AvatarType.entries

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
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onAvatarSelected(avatarType) },
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

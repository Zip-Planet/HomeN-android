package com.devndev.homen.ui.main.mypage.delegate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.devndev.homen.core.domain.model.home.Member
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNPopup
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.mypage.delegate.viewmodel.DelegateManagerContract
import com.devndev.homen.ui.main.mypage.delegate.viewmodel.DelegateManagerViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.cancel
import homen.composeapp.generated.resources.checkbox_icon
import homen.composeapp.generated.resources.delegate_manager_btn
import homen.composeapp.generated.resources.delegate_manager_current_label
import homen.composeapp.generated.resources.delegate_manager_header_subtitle
import homen.composeapp.generated.resources.delegate_manager_header_title
import homen.composeapp.generated.resources.delegate_manager_new_label
import homen.composeapp.generated.resources.delegate_manager_popup_subtitle
import homen.composeapp.generated.resources.delegate_manager_popup_title
import homen.composeapp.generated.resources.delegate_manager_title
import homen.composeapp.generated.resources.delegate_manager_toast
import homen.composeapp.generated.resources.exchange_icon
import homen.composeapp.generated.resources.university_icon
import kotlinx.coroutines.flow.collectLatest
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DelegateManagerScreen(
    onNavBack: () -> Unit,
    viewModel: DelegateManagerViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState
    val toastMsg = stringResource(Res.string.delegate_manager_toast)

    BackHandler {
        viewModel.setEvent(DelegateManagerContract.Event.OnBackClick)
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(DelegateManagerContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                DelegateManagerContract.Effect.PopBackStack -> onNavBack()
                DelegateManagerContract.Effect.DelegateSuccess -> {
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

    if (uiState.isShowPopup && uiState.selectedMember != null) {
        val selected = uiState.selectedMember!!
        HomeNPopup(
            title = stringResource(Res.string.delegate_manager_popup_title),
            message = stringResource(Res.string.delegate_manager_popup_subtitle).replace(
                "s",
                selected.name
            ),
            startButtonText = stringResource(Res.string.cancel),
            onStartButtonClick = { viewModel.setEvent(DelegateManagerContract.Event.OnDismissPopup) },
            endButtonText = stringResource(Res.string.delegate_manager_btn),
            onEndButtonClick = { viewModel.setEvent(DelegateManagerContract.Event.OnConfirmDelegate) },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BackgroundGray)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val currentAvatarRes =
                            AvatarType.fromId(uiState.myProfileImage ?: 1).resource
                        Image(
                            painter = painterResource(currentAvatarRes),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = stringResource(Res.string.delegate_manager_current_label).replace(
                                "s",
                                uiState.currentManagerName
                            ),
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .background(Color.White, RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.exchange_icon),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Black
                        )
                    }


                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val avatarRes = AvatarType.fromId(selected.profileImage ?: 1).resource

                        Image(
                            painter = painterResource(avatarRes),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Text(
                            text = stringResource(Res.string.delegate_manager_new_label).replace(
                                "s",
                                selected.name
                            ),
                            style = HomeNTheme.typography.suitBold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    }
                }
            },
            onDismiss = { viewModel.setEvent(DelegateManagerContract.Event.OnDismissPopup) }
        )
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.delegate_manager_title),
                onBackClick = { viewModel.setEvent(DelegateManagerContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading,
        isNeedBottomExpanded = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = HomeNTheme.dimensions.topPadding
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = HomeNTheme.dimensions.horizontalPadding,
                        end = HomeNTheme.dimensions.horizontalPadding,
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.university_icon),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Black
                    )

                    Text(
                        text = stringResource(Res.string.delegate_manager_header_title),
                        style = HomeNTheme.typography.suitBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = stringResource(Res.string.delegate_manager_header_subtitle),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(22.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(Color.White)
                    .padding(vertical = 24.dp, horizontal = 17.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    uiState.candidates.forEach { candidate ->
                        val isSelected = candidate == uiState.selectedMember
                        CandidateItem(
                            candidate = candidate,
                            isSelected = isSelected,
                            onSelect = {
                                viewModel.setEvent(
                                    DelegateManagerContract.Event.OnMemberSelect(
                                        candidate
                                    )
                                )
                            }
                        )
                    }
                }
                HomeNButton(
                    text = stringResource(Res.string.delegate_manager_btn),
                    onClick = { viewModel.setEvent(DelegateManagerContract.Event.OnDelegateClick) },
                    enabled = uiState.isDelegateEnabled,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = HomeNTheme.dimensions.bottomPadding)
                )
            }
        }
    }
}

@Composable
private fun CandidateItem(
    candidate: Member,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onSelect() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val avatarRes = AvatarType.fromId(candidate.profileImage ?: 1).resource
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BackgroundGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(avatarRes),
                    contentDescription = null,
                    modifier = Modifier.size(26.dp)
                )
            }

            Text(
                text = candidate.name,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black
            )
        }

        Icon(
            painter = painterResource(Res.drawable.checkbox_icon),
            contentDescription = null,
            modifier = Modifier.size(17.dp),
            tint = if (isSelected) Color.Black else Color(0xFFE7EAF0)
        )
    }
}

package com.devndev.homen.ui.main.mypage.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.core.domain.model.home.HomeIconType
import com.devndev.homen.core.domain.model.home.Member
import com.devndev.homen.ui.common.bigResource
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.common.smallResource
import com.devndev.homen.ui.component.BackHandler
import com.devndev.homen.ui.component.Dot
import com.devndev.homen.ui.component.HomeNButton
import com.devndev.homen.ui.component.HomeNPopup
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.mypage.setting.viewmodel.HomeSettingContract
import com.devndev.homen.ui.main.mypage.setting.viewmodel.HomeSettingViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Blue2
import com.devndev.homen.ui.theme.BottomGray
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.cancel
import homen.composeapp.generated.resources.home_setting_cannot_leave_popup_msg
import homen.composeapp.generated.resources.home_setting_cannot_leave_popup_title
import homen.composeapp.generated.resources.home_setting_delegate_manager
import homen.composeapp.generated.resources.home_setting_disband_bullet1
import homen.composeapp.generated.resources.home_setting_disband_bullet2
import homen.composeapp.generated.resources.home_setting_disband_confirm
import homen.composeapp.generated.resources.home_setting_disband_popup_msg
import homen.composeapp.generated.resources.home_setting_disband_popup_title
import homen.composeapp.generated.resources.home_setting_leave_confirm
import homen.composeapp.generated.resources.home_setting_leave_home_btn
import homen.composeapp.generated.resources.home_setting_leave_popup_title
import homen.composeapp.generated.resources.home_setting_member_count
import homen.composeapp.generated.resources.home_setting_member_section
import homen.composeapp.generated.resources.home_setting_title
import homen.composeapp.generated.resources.manager
import homen.composeapp.generated.resources.member
import homen.composeapp.generated.resources.my_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeSettingScreen(
    onNavBack: () -> Unit,
    onNavToDelegateManager: () -> Unit = {},
    onNavToHomeIntro: () -> Unit = {},
    viewModel: HomeSettingViewModel = koinViewModel()
) {
    val uiState by viewModel.viewState

    BackHandler {
        viewModel.setEvent(HomeSettingContract.Event.OnBackClick)
    }

    LaunchedEffect(Unit) {
        viewModel.setEvent(HomeSettingContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                HomeSettingContract.Effect.PopBackStack -> onNavBack()
                HomeSettingContract.Effect.NavigateToDelegateManager -> onNavToDelegateManager()
                HomeSettingContract.Effect.NavigateToHomeIntro -> onNavToHomeIntro()
            }
        }
    }

    // Popup Handling
    when (uiState.activePopup) {
        HomeSettingContract.SettingPopupType.LEAVE -> {
            HomeNPopup(
                title = stringResource(Res.string.home_setting_leave_popup_title).replace("s", uiState.homeName),
                message = "",
                startButtonText = stringResource(Res.string.cancel),
                onStartButtonClick = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) },
                endButtonText = stringResource(Res.string.home_setting_leave_confirm),
                onEndButtonClick = { viewModel.setEvent(HomeSettingContract.Event.OnConfirmLeave) },
                onDismiss = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) }
            )
        }

        HomeSettingContract.SettingPopupType.CANNOT_LEAVE -> {
            HomeNPopup(
                title = stringResource(Res.string.home_setting_cannot_leave_popup_title).replace("s", uiState.homeName),
                message = stringResource(Res.string.home_setting_cannot_leave_popup_msg),
                startButtonText = stringResource(Res.string.cancel),
                onStartButtonClick = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) },
                endButtonText = stringResource(Res.string.home_setting_delegate_manager),
                onEndButtonClick = {
                    viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup)
                    viewModel.setEvent(HomeSettingContract.Event.OnDelegateManagerClick)
                },
                onDismiss = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) }
            )
        }

        HomeSettingContract.SettingPopupType.DISBAND -> {
            HomeNPopup(
                title = stringResource(Res.string.home_setting_disband_popup_title).replace("s", uiState.homeName),
                message = stringResource(Res.string.home_setting_disband_popup_msg),
                startButtonText = stringResource(Res.string.cancel),
                onStartButtonClick = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) },
                endButtonText = stringResource(Res.string.home_setting_disband_confirm),
                onEndButtonClick = { viewModel.setEvent(HomeSettingContract.Event.OnConfirmDisband) },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(vertical = 20.dp, horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Dot(
                                width = 4,
                                height = 3,
                                dotSize = 3
                            )
                            Text(
                                text = stringResource(Res.string.home_setting_disband_bullet1),
                                style = HomeNTheme.typography.suitRegular,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Dot(
                                width = 4,
                                height = 3,
                                dotSize = 3
                            )
                            Text(
                                text = stringResource(Res.string.home_setting_disband_bullet2),
                                style = HomeNTheme.typography.suitRegular,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                },
                onDismiss = { viewModel.setEvent(HomeSettingContract.Event.OnDismissPopup) }
            )
        }

        HomeSettingContract.SettingPopupType.NONE -> {}
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.home_setting_title),
                onBackClick = { viewModel.setEvent(HomeSettingContract.Event.OnBackClick) }
            )
        },
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = HomeNTheme.dimensions.horizontalPadding,
                    end = HomeNTheme.dimensions.horizontalPadding,
                    bottom = HomeNTheme.dimensions.bottomPadding,
                    top = HomeNTheme.dimensions.topPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Home Header Icon & Name
            val homeIconRes = HomeIconType.fromId(uiState.homeImageId).smallResource
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(homeIconRes),
                    contentDescription = null,
                    modifier = Modifier.size(33.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = uiState.homeName,
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = stringResource(Res.string.home_setting_member_count).replace("n", uiState.members.size.toString()),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Members Section Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.my_icon),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = stringResource(Res.string.home_setting_member_section),
                        style = HomeNTheme.typography.suitExtraBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(13.dp))

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    uiState.members.forEach { member ->
                        MemberItem(
                            member = member,
                            isMe = member.name == uiState.myName
                        )
                    }
                }

                if (uiState.isManager) {
                    Spacer(modifier = Modifier.height(13.dp))
                    HomeNButton(
                        text = stringResource(Res.string.home_setting_delegate_manager),
                        onClick = { viewModel.setEvent(HomeSettingContract.Event.OnDelegateManagerClick) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Leave Home Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable { viewModel.setEvent(HomeSettingContract.Event.OnLeaveClick) }
                    .padding(vertical = 20.dp, horizontal = 15.dp)
            ) {
                Text(
                    text = stringResource(Res.string.home_setting_leave_home_btn).replace("s", uiState.homeName),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun MemberItem(
    member: Member,
    isMe: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val avatarRes = AvatarType.fromId(member.profileImage ?: 1).resource
            Image(
                painter = painterResource(avatarRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            val displayName = if (isMe) "${member.name} · 나" else member.name
            Text(
                text = displayName,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        val isManagerRole = member.role == 1
        val text = if (isManagerRole) stringResource(Res.string.manager) else stringResource(Res.string.member)
        val backgroundColor = if (isManagerRole) Blue2 else ButtonGray
        val textColor = if (isManagerRole) Color.White else Color.Black

        Box(
            modifier = Modifier
                .background(color = backgroundColor, shape = RoundedCornerShape(13.dp))
                .padding(vertical = 2.dp, horizontal = 5.dp)
        ) {
            Text(
                text = text,
                style = HomeNTheme.typography.suitRegular,
                fontSize = 10.sp,
                color = textColor
            )
        }
    }
}

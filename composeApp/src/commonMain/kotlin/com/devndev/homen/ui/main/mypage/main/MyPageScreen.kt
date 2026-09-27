package com.devndev.homen.ui.main.mypage.main

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.home.AvatarType
import com.devndev.homen.ui.common.resource
import com.devndev.homen.ui.component.HomeNPopup
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.NotificationTopBar
import com.devndev.homen.ui.main.mypage.main.viewmodel.MyPageContract
import com.devndev.homen.ui.main.mypage.main.viewmodel.MyPageViewModel
import com.devndev.homen.ui.theme.Blue2
import com.devndev.homen.ui.theme.ButtonGray
import com.devndev.homen.ui.theme.HomeNTheme
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.cancel
import homen.composeapp.generated.resources.clipboard_copy_toast
import homen.composeapp.generated.resources.copy_icon
import homen.composeapp.generated.resources.logout
import homen.composeapp.generated.resources.manager
import homen.composeapp.generated.resources.member
import homen.composeapp.generated.resources.my_page
import homen.composeapp.generated.resources.my_page_invite_code_format
import homen.composeapp.generated.resources.my_page_logout_confirm_btn
import homen.composeapp.generated.resources.my_page_logout_popup_title
import homen.composeapp.generated.resources.my_page_push_alarm
import homen.composeapp.generated.resources.my_page_push_assignment
import homen.composeapp.generated.resources.my_page_push_board
import homen.composeapp.generated.resources.my_page_push_home
import homen.composeapp.generated.resources.my_page_push_report
import homen.composeapp.generated.resources.my_page_push_reward
import homen.composeapp.generated.resources.my_page_support
import homen.composeapp.generated.resources.navigate_next_icon
import homen.composeapp.generated.resources.setting_icon
import homen.composeapp.generated.resources.share_icon
import homen.composeapp.generated.resources.switch_off_icon
import homen.composeapp.generated.resources.switch_on_icon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyPageScreen(
    viewModel: MyPageViewModel = koinViewModel(),
    onNavToLogin: () -> Unit,
    onNavToProfileSetting: (String, Int) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.viewState
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.setEvent(MyPageContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MyPageContract.Effect.NavigateToProfileSetting -> {
                    onNavToProfileSetting(effect.nickname, effect.avatarId)
                }
                MyPageContract.Effect.NavigateToHomeSetting -> {}
                MyPageContract.Effect.NavigateToSupport -> {}
                MyPageContract.Effect.NavigateToSplash -> {
                    onNavToLogin()
                }
            }
        }
    }

    if (uiState.isShowLogoutPopup) {
        HomeNPopup(
            title = stringResource(Res.string.my_page_logout_popup_title),
            message = "",
            startButtonText = stringResource(Res.string.cancel),
            onStartButtonClick = {
                viewModel.setEvent(MyPageContract.Event.OnDismissPopup)
            },
            endButtonText = stringResource(Res.string.my_page_logout_confirm_btn),
            onEndButtonClick = {
                viewModel.setEvent(MyPageContract.Event.OnLogoutClick(true))

            },
            onDismiss = {
                viewModel.setEvent(MyPageContract.Event.OnDismissPopup)
            }
        )
    }

    HomeNScreen(
        topBar = {
            NotificationTopBar(
                title = stringResource(Res.string.my_page),
                onNotificationClick = {}
            )
        },
        mainIsLoading = uiState.mainIsLoading,
        isLoading = uiState.isLoading
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = HomeNTheme.dimensions.horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(27.dp))

            // Profile Section
            val avatarRes = AvatarType.fromId(uiState.avatarId ?: 1).resource
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(avatarRes),
                    contentDescription = null,
                    modifier = Modifier.size(37.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = uiState.nickname,
                    style = HomeNTheme.typography.suitExtraBold,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Icon(
                    painter = painterResource(Res.drawable.setting_icon),
                    contentDescription = "Profile Setting",
                    modifier = Modifier
                        .size(14.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { viewModel.setEvent(MyPageContract.Event.OnProfileSettingClick) },
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = uiState.homeName,
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                var text = stringResource(Res.string.manager)
                var backgroundColor = Blue2
                var textColor = Color.White

                if (uiState.homeRole == 2) {
                    text = stringResource(Res.string.member)
                    backgroundColor = ButtonGray
                    textColor = Color.Black
                }

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

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    painter = painterResource(Res.drawable.setting_icon),
                    contentDescription = "Home Setting",
                    modifier = Modifier
                        .size(14.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { viewModel.setEvent(MyPageContract.Event.OnHomeSettingClick) },
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(Res.string.my_page_invite_code_format).replace(
                        "s",
                        uiState.inviteCode
                    ),
                    style = HomeNTheme.typography.suitBold,
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(17.dp))
                val toastMsg = stringResource(Res.string.clipboard_copy_toast)
                Icon(
                    painter = painterResource(Res.drawable.copy_icon),
                    contentDescription = "Copy",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            scope.launch {
                                clipboard.setText(AnnotatedString(uiState.inviteCode))
                            }
                            showToast(
                                message = toastMsg,
                                backgroundColor = Color.Black.copy(alpha = 0.8f),
                                textColor = Color.White,
                                cornerRadius = 10,
                                duration = ToastDuration.Short
                            )
                        },
                    tint = Color.Black
                )

                Spacer(modifier = Modifier.width(7.dp))

                Icon(
                    painter = painterResource(Res.drawable.share_icon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .size(14.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            viewModel.setEvent(MyPageContract.Event.OnShareClick)
                        },
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Menu Items
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PushSettingSection(
                    isEnabled = uiState.isPushEnabled,
                    isExpanded = uiState.isPushDetailExpanded,
                    onToggle = { viewModel.setEvent(MyPageContract.Event.OnPushToggle) },
                    onSubToggle = { type ->
                        viewModel.setEvent(
                            MyPageContract.Event.OnSubPushToggle(
                                type
                            )
                        )
                    },
                    state = uiState
                )

                MenuItem(
                    title = stringResource(Res.string.my_page_support),
                    onClick = { viewModel.setEvent(MyPageContract.Event.OnSupportClick) },
                    showArrow = true
                )

                MenuItem(
                    title = stringResource(Res.string.logout),
                    onClick = { viewModel.setEvent(MyPageContract.Event.OnLogoutClick(false)) },
                    showArrow = false
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun HomeNSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    width: Dp = 27.dp,
    height: Dp = 17.dp,
    modifier: Modifier = Modifier
) {
    val icon = if (checked) Res.drawable.switch_on_icon else Res.drawable.switch_off_icon

    Image(
        painter = painterResource(icon),
        contentDescription = null,
        modifier = modifier
            .size(width = width, height = height)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCheckedChange(!checked)
            }
    )
}

@Composable
fun PushSettingSection(
    isEnabled: Boolean,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onSubToggle: (MyPageContract.PushType) -> Unit,
    state: MyPageContract.State
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(Res.string.my_page_push_alarm),
                style = HomeNTheme.typography.suitBold,
                fontSize = 14.sp,
                color = Color.Black
            )
            HomeNSwitch(
                checked = isEnabled,
                onCheckedChange = { onToggle() }
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column(
                modifier = Modifier.padding(top = 13.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                SubPushItem(
                    title = stringResource(Res.string.my_page_push_home),
                    isChecked = state.isHomeAlarmEnabled,
                    onCheckedChange = { onSubToggle(MyPageContract.PushType.HOME) })
                SubPushItem(
                    title = stringResource(Res.string.my_page_push_assignment),
                    isChecked = state.isAssignmentAlarmEnabled,
                    onCheckedChange = { onSubToggle(MyPageContract.PushType.ASSIGNMENT) })
                SubPushItem(
                    title = stringResource(Res.string.my_page_push_board),
                    isChecked = state.isBoardAlarmEnabled,
                    onCheckedChange = { onSubToggle(MyPageContract.PushType.BOARD) })
                SubPushItem(
                    title = stringResource(Res.string.my_page_push_reward),
                    isChecked = state.isRewardAlarmEnabled,
                    onCheckedChange = { onSubToggle(MyPageContract.PushType.REWARD) })
                SubPushItem(
                    title = stringResource(Res.string.my_page_push_report),
                    isChecked = state.isReportAlarmEnabled,
                    onCheckedChange = { onSubToggle(MyPageContract.PushType.REPORT) })
            }
        }
    }
}

@Composable
fun SubPushItem(
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = HomeNTheme.typography.suitMedium,
            fontSize = 12.sp,
            color = Color.Black
        )
        HomeNSwitch(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(it) },
            width = 21.dp,
            height = 13.dp
        )
    }
}

@Composable
fun MenuItem(
    title: String,
    onClick: () -> Unit,
    showArrow: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 15.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = HomeNTheme.typography.suitBold,
            fontSize = 14.sp,
            color = Color.Black
        )
        if (showArrow) {
            Icon(
                painter = painterResource(Res.drawable.navigate_next_icon),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = Color.Black
            )
        }
    }
}

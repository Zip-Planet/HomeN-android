package com.devndev.homen.ui.main.notification

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
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devndev.homen.core.domain.model.notification.NotificationCategory
import com.devndev.homen.core.domain.model.notification.NotificationItem
import com.devndev.homen.ui.component.HomeNScreen
import com.devndev.homen.ui.component.TitleTopBar
import com.devndev.homen.ui.main.notification.viewmodel.NotificationInboxContract
import com.devndev.homen.ui.main.notification.viewmodel.NotificationInboxViewModel
import com.devndev.homen.ui.theme.BackgroundGray
import com.devndev.homen.ui.theme.Gray7C
import com.devndev.homen.ui.theme.HomeNTheme
import com.devndev.homen.util.DateUtil
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.alarm_assignment_icon
import homen.composeapp.generated.resources.alarm_board_icon
import homen.composeapp.generated.resources.alarm_chat_icon
import homen.composeapp.generated.resources.alarm_check_day_label
import homen.composeapp.generated.resources.alarm_member_icon
import homen.composeapp.generated.resources.alarm_present_icon
import homen.composeapp.generated.resources.alarm_report_icon
import homen.composeapp.generated.resources.alarm_title
import homen.composeapp.generated.resources.bottom_arrow_icon
import homen.composeapp.generated.resources.calendar_icon
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.home_icon
import homen.composeapp.generated.resources.menu_icon
import homen.composeapp.generated.resources.present_icon
import homen.composeapp.generated.resources.top_arrow_icon
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotificationInboxScreen(
    viewModel: NotificationInboxViewModel = koinViewModel(),
    onNavBack: () -> Unit,
    onNavigateToDeepLink: (String) -> Unit = {}
) {
    val uiState by viewModel.viewState

    LaunchedEffect(Unit) {
        viewModel.setEvent(NotificationInboxContract.Event.OnInit)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationInboxContract.Effect.PopBackStack -> {
                    onNavBack()
                }
                is NotificationInboxContract.Effect.NavigateToDeepLink -> {
                    onNavigateToDeepLink(effect.deepLink)
                }
            }
        }
    }

    HomeNScreen(
        topBar = {
            TitleTopBar(
                title = stringResource(Res.string.alarm_title),
                onBackClick = { viewModel.setEvent(NotificationInboxContract.Event.OnBackClick) }
            )
        },
        mainIsLoading = uiState.isLoading,
        isNeedBottomExpanded = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HomeNTheme.dimensions.horizontalPadding)
            ) {
                CategoryFilterBar(
                    selectedCategory = uiState.selectedCategory,
                    isExpanded = uiState.isDropdownExpanded,
                    onToggleDropdown = { viewModel.setEvent(NotificationInboxContract.Event.OnToggleFilterDropdown) },
                    onSelectCategory = { category ->
                        viewModel.setEvent(NotificationInboxContract.Event.OnSelectCategory(category))
                    },
                    onDismissDropdown = { viewModel.setEvent(NotificationInboxContract.Event.OnDismissFilterDropdown) },
                    iconSize = uiState.iconSize
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Single White Card Container wrapping ALL Notifications (Image 1)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .background(Color.White)
                    .padding(vertical = 24.dp, horizontal = 17.dp)
            ) {
                if (uiState.notifications.isEmpty() && !uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "알림이 없어요",
                            style = HomeNTheme.typography.suitMedium,
                            fontSize = 14.sp,
                            color = Gray7C
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 108.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        uiState.notifications.forEach { notification ->
                            NotificationListItem(
                                item = notification,
                                onClick = {
                                    viewModel.setEvent(
                                        NotificationInboxContract.Event.OnNotificationClick(notification)
                                    )
                                }
                            )
                        }
                    }
                }

                Text(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp),
                    text = stringResource(Res.string.alarm_check_day_label),
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 12.sp,
                    color = Color.Black,
                )
            }
        }
    }
}

@Composable
private fun CategoryFilterBar(
    selectedCategory: NotificationCategory,
    isExpanded: Boolean,
    onToggleDropdown: () -> Unit,
    onSelectCategory: (NotificationCategory) -> Unit,
    onDismissDropdown: () -> Unit,
    iconSize: Int
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd // Box 전체를 우측 상단 정렬 기준으로 설정
    ) {
        // 1. 전체 클릭 가능한 상단 필터 바
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onToggleDropdown() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(getMenuCategoryIcon(selectedCategory)),
                contentDescription = null,
                modifier = Modifier.size(iconSize.dp),
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = selectedCategory.label,
                style = HomeNTheme.typography.suitBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(
                    if (isExpanded) Res.drawable.top_arrow_icon else Res.drawable.bottom_arrow_icon
                ),
                contentDescription = "Dropdown",
                modifier = Modifier.size(12.dp),
                tint = Color.Black
            )
        }

        // 2. 우측 끝(화살표 아이콘 영역) 기준으로 열리는 DropdownMenu
        // DropdownMenu를 박스의 오른쪽 끝에 정렬하기 위해 Box의 Alignment.TopEnd 아래에 위치시킵니다.
        Box(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = onDismissDropdown,
                offset = DpOffset(x = 0.dp, y = 8.dp),
                // 1. 기본 Container 배경색을 투명으로 설정
                containerColor = Color.Transparent,
                // 2. Material 기본 Elevation(음영/회색 톤) 제거
                tonalElevation = 0.dp,
                shadowElevation = 3.dp,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 0.dp,
                            bottomEnd = 10.dp,
                            bottomStart = 10.dp
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NotificationCategory.entries.forEach { category ->
                        Row(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    onSelectCategory(category)
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(getMenuCategoryIcon(category)),
                                contentDescription = null,
                                modifier = Modifier.size(iconSize.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = category.label,
                                style = HomeNTheme.typography.suitMedium,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationListItem(
    item: NotificationItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() },
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(BackgroundGray),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(getCategoryIcon(item.category)),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(7.dp))

        // Title and Body
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title,
                style = HomeNTheme.typography.suitBold,
                fontSize = 13.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            val body = item.body
            if (!body.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = body,
                    style = HomeNTheme.typography.suitRegular,
                    fontSize = 10.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Text(
            text = DateUtil.formatRelativeTime(item.createdAt),
            style = HomeNTheme.typography.suitRegular,
            fontSize = 10.sp,
            color = Color.Black
        )
    }
}

private fun getMenuCategoryIcon(category: NotificationCategory): DrawableResource {
    return when (category) {
        NotificationCategory.ALL -> Res.drawable.menu_icon
        NotificationCategory.HOME_MEMBER -> Res.drawable.home_icon
        NotificationCategory.ASSIGNMENT -> Res.drawable.chart_icon
        NotificationCategory.BOARD -> Res.drawable.alarm_chat_icon
        NotificationCategory.REWARD -> Res.drawable.present_icon
        NotificationCategory.REPORT -> Res.drawable.clipboard_icon
    }
}

private fun getCategoryIcon(category: NotificationCategory): DrawableResource {
    return when (category) {
        NotificationCategory.ALL -> Res.drawable.home_icon
        NotificationCategory.HOME_MEMBER -> Res.drawable.alarm_member_icon
        NotificationCategory.ASSIGNMENT -> Res.drawable.alarm_assignment_icon
        NotificationCategory.BOARD -> Res.drawable.alarm_board_icon
        NotificationCategory.REWARD -> Res.drawable.alarm_present_icon
        NotificationCategory.REPORT -> Res.drawable.alarm_report_icon
    }
}

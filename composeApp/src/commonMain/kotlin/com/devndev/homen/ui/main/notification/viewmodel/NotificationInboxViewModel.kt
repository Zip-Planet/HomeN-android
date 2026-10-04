package com.devndev.homen.ui.main.notification.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.notification.NotificationCategory
import com.devndev.homen.core.domain.model.notification.NotificationItem
import com.devndev.homen.core.domain.usecase.notification.GetNotificationsUseCase
import com.devndev.homen.core.domain.usecase.notification.ReadNotificationUseCase
import kotlinx.coroutines.launch

class NotificationInboxViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val readNotificationUseCase: ReadNotificationUseCase
) : BaseViewModel<NotificationInboxContract.Event, NotificationInboxContract.State, NotificationInboxContract.Effect>() {

    override fun setInitialState() = NotificationInboxContract.State()

    override fun handleEvents(event: NotificationInboxContract.Event) {
        when (event) {
            NotificationInboxContract.Event.OnInit -> {
                fetchNotifications(viewState.value.selectedCategory)
            }

            is NotificationInboxContract.Event.OnSelectCategory -> {
                setState {
                    copy(
                        selectedCategory = event.category,
                        isDropdownExpanded = false
                    )
                }
                fetchNotifications(event.category)
            }

            is NotificationInboxContract.Event.OnNotificationClick -> {
                onNotificationClick(event.item)
            }

            NotificationInboxContract.Event.OnToggleFilterDropdown -> {
                setState { copy(isDropdownExpanded = !isDropdownExpanded) }
            }

            NotificationInboxContract.Event.OnDismissFilterDropdown -> {
                setState { copy(isDropdownExpanded = false) }
            }

            NotificationInboxContract.Event.OnBackClick -> {
                setEffect { NotificationInboxContract.Effect.PopBackStack }
            }
        }
    }

    private fun onNotificationClick(item: NotificationItem) {
        viewModelScope.launch {
            // 1. Call Read Notification API
            readNotificationUseCase(item.id)

            // 2. Locally update read status in state
            val updatedNotifications = viewState.value.notifications.map {
                if (it.id == item.id) it.copy(isRead = true) else it
            }
            setState { copy(notifications = updatedNotifications) }

            // 3. Navigate to corresponding category screen
            setEffect { NotificationInboxContract.Effect.NavigateToCategory(item.category) }
        }
    }

    private fun fetchNotifications(category: NotificationCategory) {
        viewModelScope.launch {
            val categoryKey = if (category == NotificationCategory.ALL) null else category.key
            if (viewState.value.isInit) {
                setState { copy(isLoading = true, isInit = false) }
            }
//            val temp = listOf(
//                NotificationItem(
//                    id = 101,
//                    category = NotificationCategory.HOME_MEMBER,
//                    categoryLabel = "집·구성원",
//                    title = "새 구성원이 참여했어요",
//                    body = "닉네임님이 집이름에 참여했어요",
//                    deepLink = "home_member:101",
//                    isRead = false,
//                    createdAt = "2026-10-01T10:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 102,
//                    category = NotificationCategory.ASSIGNMENT,
//                    categoryLabel = "분담안",
//                    title = "다음 주 분담안이 생성됐어요",
//                    body = "1월 2주차 분담안을 확인해주세요",
//                    deepLink = "assignment:2026-10-05",
//                    isRead = false,
//                    createdAt = "2026-10-01T09:30:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 103,
//                    category = NotificationCategory.ASSIGNMENT,
//                    categoryLabel = "분담안",
//                    title = "이번 주 분담안을 기다리고 있어요",
//                    body = "[닉네임]님이 분담안 생성을 기다리고 있어요.\n이번 주 분담안을 생성해볼까요?",
//                    deepLink = "assignment:2026-09-28",
//                    isRead = false,
//                    createdAt = "2026-10-01T09:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 104,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "[닉네임] 도움이 필요해요",
//                    body = "메세지 : 토요일 출장이라 대신해 줄 사람?",
//                    deepLink = "board:104",
//                    isRead = false,
//                    createdAt = "2026-10-01T08:15:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 105,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "교환요청이 도착했어요",
//                    body = "[닉네임1]님이 [닉네임2]님께 교환 요청 했어요",
//                    deepLink = "board:105",
//                    isRead = false,
//                    createdAt = "2026-10-01T07:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 106,
//                    category = NotificationCategory.REWARD,
//                    categoryLabel = "리워드",
//                    title = "리워드가 수령됐어요 [닉네임]",
//                    body = "구성원이 [집안일 청소 패스권] 리워드를 받았어요",
//                    deepLink = "reward:106",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 107,
//                    category = NotificationCategory.REPORT,
//                    categoryLabel = "리포트",
//                    title = "이번 주 리포트가 도착했어요",
//                    body = "1월 2주차 진행률과 우리집 MVP를 확인해보세요",
//                    deepLink = "report:2026-09-28",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 104,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "[닉네임] 도움이 필요해요",
//                    body = "메세지 : 토요일 출장이라 대신해 줄 사람?",
//                    deepLink = "board:104",
//                    isRead = false,
//                    createdAt = "2026-10-01T08:15:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 105,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "교환요청이 도착했어요",
//                    body = "[닉네임1]님이 [닉네임2]님께 교환 요청 했어요",
//                    deepLink = "board:105",
//                    isRead = false,
//                    createdAt = "2026-10-01T07:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 106,
//                    category = NotificationCategory.REWARD,
//                    categoryLabel = "리워드",
//                    title = "리워드가 수령됐어요 [닉네임]",
//                    body = "구성원이 [집안일 청소 패스권] 리워드를 받았어요",
//                    deepLink = "reward:106",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 107,
//                    category = NotificationCategory.REPORT,
//                    categoryLabel = "리포트",
//                    title = "이번 주 리포트가 도착했어요",
//                    body = "1월 2주차 진행률과 우리집 MVP를 확인해보세요",
//                    deepLink = "report:2026-09-28",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 104,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "[닉네임] 도움이 필요해요",
//                    body = "메세지 : 토요일 출장이라 대신해 줄 사람?",
//                    deepLink = "board:104",
//                    isRead = false,
//                    createdAt = "2026-10-01T08:15:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 105,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "교환요청이 도착했어요",
//                    body = "[닉네임1]님이 [닉네임2]님께 교환 요청 했어요",
//                    deepLink = "board:105",
//                    isRead = false,
//                    createdAt = "2026-10-01T07:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 106,
//                    category = NotificationCategory.REWARD,
//                    categoryLabel = "리워드",
//                    title = "리워드가 수령됐어요 [닉네임]",
//                    body = "구성원이 [집안일 청소 패스권] 리워드를 받았어요",
//                    deepLink = "reward:106",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 107,
//                    category = NotificationCategory.REPORT,
//                    categoryLabel = "리포트",
//                    title = "이번 주 리포트가 도착했어요",
//                    body = "1월 2주차 진행률과 우리집 MVP를 확인해보세요",
//                    deepLink = "report:2026-09-28",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 104,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "[닉네임] 도움이 필요해요",
//                    body = "메세지 : 토요일 출장이라 대신해 줄 사람?",
//                    deepLink = "board:104",
//                    isRead = false,
//                    createdAt = "2026-10-01T08:15:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 105,
//                    category = NotificationCategory.BOARD,
//                    categoryLabel = "보드 조율",
//                    title = "교환요청이 도착했어요",
//                    body = "[닉네임1]님이 [닉네임2]님께 교환 요청 했어요",
//                    deepLink = "board:105",
//                    isRead = false,
//                    createdAt = "2026-10-01T07:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 106,
//                    category = NotificationCategory.REWARD,
//                    categoryLabel = "리워드",
//                    title = "리워드가 수령됐어요 [닉네임]",
//                    body = "구성원이 [집안일 청소 패스권] 리워드를 받았어요",
//                    deepLink = "reward:106",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                ),
//                NotificationItem(
//                    id = 107,
//                    category = NotificationCategory.REPORT,
//                    categoryLabel = "리포트",
//                    title = "이번 주 리포트가 도착했어요",
//                    body = "1월 2주차 진행률과 우리집 MVP를 확인해보세요",
//                    deepLink = "report:2026-09-28",
//                    isRead = true,
//                    createdAt = "2026-10-01T05:00:00.000000Z"
//                )
//            )
            when (val result = getNotificationsUseCase(categoryKey)) {
                is ApiResult.Success -> {
                    setState {
                        copy(
                            unreadCount = result.data.unreadCount,
                            retentionDays = result.data.retentionDays,
                            notifications = result.data.notifications
                        )
                    }
                }

                is ApiResult.Error, ApiResult.NetworkError -> {
                    // Handle error if needed
                }
            }
            setState { copy(isLoading = false) }
        }
    }
}

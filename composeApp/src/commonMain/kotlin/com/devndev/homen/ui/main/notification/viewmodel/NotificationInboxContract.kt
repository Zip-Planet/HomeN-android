package com.devndev.homen.ui.main.notification.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.notification.NotificationCategory
import com.devndev.homen.core.domain.model.notification.NotificationItem

class NotificationInboxContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data class OnSelectCategory(val category: NotificationCategory) : Event()
        data class OnNotificationClick(val item: NotificationItem) : Event()
        data object OnToggleFilterDropdown : Event()
        data object OnDismissFilterDropdown : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val isInit: Boolean = false,
        val selectedCategory: NotificationCategory = NotificationCategory.ALL,
        val isDropdownExpanded: Boolean = false,
        val unreadCount: Int = 0,
        val retentionDays: Int = 7,
        val notifications: List<NotificationItem> = emptyList()
    ) : ViewState {
        val iconSize = when (selectedCategory) {
            NotificationCategory.ALL,
            NotificationCategory.BOARD -> 22
            else -> 20
        }
    }

    sealed class Effect : ViewSideEffect {
        data object PopBackStack : Effect()
        data class NavigateToDeepLink(val deepLink: String) : Effect()
    }
}

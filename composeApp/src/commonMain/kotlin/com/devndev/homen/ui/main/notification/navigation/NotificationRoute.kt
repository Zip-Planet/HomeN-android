package com.devndev.homen.ui.main.notification.navigation

import kotlinx.serialization.Serializable

sealed class NotificationRoute {
    @Serializable
    data object NotificationInbox : NotificationRoute()
}

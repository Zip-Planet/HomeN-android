package com.devndev.homen.core.data.model.notification.response

import com.devndev.homen.core.domain.model.notification.NotificationCategory
import com.devndev.homen.core.domain.model.notification.NotificationItem
import com.devndev.homen.core.domain.model.notification.NotificationList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationListResponse(
    @SerialName("unread_count")
    val unreadCount: Int,
    @SerialName("retention_days")
    val retentionDays: Int,
    @SerialName("notifications")
    val notifications: List<NotificationItemResponse>
)

@Serializable
data class NotificationItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("category")
    val category: String,
    @SerialName("category_label")
    val categoryLabel: String,
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String? = null,
    @SerialName("deep_link")
    val deepLink: String? = null,
    @SerialName("is_read")
    val isRead: Boolean,
    @SerialName("created_at")
    val createdAt: String
)

fun NotificationListResponse.toDomainModel(): NotificationList {
    return NotificationList(
        unreadCount = this.unreadCount,
        retentionDays = this.retentionDays,
        notifications = this.notifications.map { it.toDomainModel() }
    )
}

fun NotificationItemResponse.toDomainModel(): NotificationItem {
    return NotificationItem(
        id = this.id,
        category = NotificationCategory.fromKey(this.category),
        categoryLabel = this.categoryLabel,
        title = this.title,
        body = this.body,
        deepLink = this.deepLink,
        isRead = this.isRead,
        createdAt = this.createdAt
    )
}

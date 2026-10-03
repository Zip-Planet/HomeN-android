package com.devndev.homen.core.domain.model.notification

data class NotificationList(
    val unreadCount: Int,
    val retentionDays: Int,
    val notifications: List<NotificationItem>
)

data class NotificationItem(
    val id: Int,
    val category: NotificationCategory,
    val categoryLabel: String,
    val title: String,
    val body: String?,
    val deepLink: String?,
    val isRead: Boolean,
    val createdAt: String
)

enum class NotificationCategory(val key: String, val label: String) {
    ALL("all", "전체"),
    HOME_MEMBER("home_member", "집·구성원"),
    ASSIGNMENT("assignment", "분담안"),
    BOARD("board", "보드 조율"),
    REWARD("reward", "리워드"),
    REPORT("report", "리포트");

    companion object {
        fun fromKey(key: String): NotificationCategory {
            return entries.find { it.key == key } ?: HOME_MEMBER
        }
    }
}

package com.devndev.homen.core.domain.model.notification

data class NotificationSetting(
    val pushEnabled: Boolean,
    val homeMember: Boolean,
    val assignment: Boolean,
    val board: Boolean,
    val reward: Boolean,
    val report: Boolean
)

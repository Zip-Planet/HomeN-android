package com.devndev.homen.core.data.model.notification.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNotificationSettingRequest(
    @SerialName("push_enabled")
    val pushEnabled: Boolean? = null,
    @SerialName("home_member")
    val homeMember: Boolean? = null,
    @SerialName("assignment")
    val assignment: Boolean? = null,
    @SerialName("board")
    val board: Boolean? = null,
    @SerialName("reward")
    val reward: Boolean? = null,
    @SerialName("report")
    val report: Boolean? = null
)

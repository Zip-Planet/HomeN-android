package com.devndev.homen.core.data.model.notification.response

import com.devndev.homen.core.domain.model.notification.NotificationSetting
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationSettingResponse(
    @SerialName("push_enabled")
    val pushEnabled: Boolean,
    @SerialName("home_member")
    val homeMember: Boolean,
    @SerialName("assignment")
    val assignment: Boolean,
    @SerialName("board")
    val board: Boolean,
    @SerialName("reward")
    val reward: Boolean,
    @SerialName("report")
    val report: Boolean
)

fun NotificationSettingResponse.toDomainModel(): NotificationSetting {
    return NotificationSetting(
        pushEnabled = this.pushEnabled,
        homeMember = this.homeMember,
        assignment = this.assignment,
        board = this.board,
        reward = this.reward,
        report = this.report
    )
}

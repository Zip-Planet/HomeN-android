package com.devndev.homen.core.data.model.home.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinHomeRequest(
    @SerialName("invite_code")
    val inviteCode: String
)

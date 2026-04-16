package com.devndev.homen.core.data.model.auth.request

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequest(
    val code: String
)

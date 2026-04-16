package com.devndev.homen.core.data.model.auth.response

import com.devndev.homen.core.domain.model.auth.AuthToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginResponse(
    val access: String,
    val refresh: String,
    @SerialName("is_profile_set")
    val isProfileSet: Boolean
)

fun KakaoLoginResponse.toDomainModel(): AuthToken {
    return AuthToken(
        accessToken = this.access,
        refreshToken = this.refresh,
        isProfileSet = this.isProfileSet
    )
}
package com.devndev.homen.core.data.model.user.response

import com.devndev.homen.core.domain.model.user.MyInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMyInfoResponse(
    @SerialName("uid")
    val uid: String, // 유저 고유 식별자 (UUID)

    @SerialName("name")
    val name: String, // 닉네임 (1~8자, 미설정 시 빈 문자열)

    @SerialName("profile_image")
    val profileImage: Int?, // 프로필 이미지 enum (1~8, 미설정 시 null)

    @SerialName("is_profile_set")
    val isProfileSet: Boolean, // 닉네임+이미지 모두 설정 여부

    @SerialName("has_home")
    val hasHome: Boolean, // 집 소속 여부

    @SerialName("home_role")
    val homeRole: Int? // 1=관리자, 2=구성원, 집 없으면 null
)

fun GetMyInfoResponse.toDomainModel(): MyInfo {
    return MyInfo(
        uid = this.uid,
        name = this.name,
        profileImage = this.profileImage,
        isProfileSet = this.isProfileSet,
        hasHome = this.hasHome,
        homeRole = this.homeRole
    )
}
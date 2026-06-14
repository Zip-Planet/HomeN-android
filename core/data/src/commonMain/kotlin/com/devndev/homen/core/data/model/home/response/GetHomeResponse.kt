package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.Member
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHomeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: Int, // 1~8
    @SerialName("invite_code")
    val inviteCode: String, // 6자리 대문자+숫자
    @SerialName("status")
    val status: String, // active 또는 draft
    @SerialName("created_at")
    val createdAt: String, // ISO 8601
    @SerialName("members")
    val members: List<HomeMemberResponse>
)

@Serializable
data class HomeMemberResponse(
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?, // null 가능
    @SerialName("role")
    val role: Int, // 1=관리자, 2=구성원
    @SerialName("role_label")
    val roleLabel: String // '관리자' 또는 '구성원'
)

fun GetHomeResponse.toDomainModel(): HomeResponseDomainModel {
    return HomeResponseDomainModel(
        id = this.id,
        name = this.name,
        image = this.image,
        inviteCode = this.inviteCode,
        status = this.status,
        createdAt = this.createdAt,
        members = this.members.map { it.toDomainModel() }
    )
}

fun HomeMemberResponse.toDomainModel(): Member {
    return Member(
        name = this.name,
        profileImage = this.profileImage,
        role = this.role,
        roleLabel = this.roleLabel
    )
}
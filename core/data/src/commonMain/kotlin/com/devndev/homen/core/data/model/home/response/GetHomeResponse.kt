package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHomeResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val image: Int,
    @SerialName("invite_code")
    val inviteCode: String,
    @SerialName("status")
    val status: String,
    @SerialName("created_at")
    val createdAt: String
)

fun GetHomeResponse.toDomainModel(): HomeResponseDomainModel {
    return HomeResponseDomainModel(
        id = this.id,
        name = this.name,
        image = this.image,
        inviteCode = this.name,
        status = this.status,
        createdAt = this.createdAt
    )
}
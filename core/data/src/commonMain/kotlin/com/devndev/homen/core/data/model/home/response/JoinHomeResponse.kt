package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.JoinHomeResponseDomainModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinHomeResponse(
    @SerialName("name")
    val name: String,
    @SerialName("image")
    val imageId: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("members")
    val members: List<HomeMemberResponse>
)

fun JoinHomeResponse.toDomainModel(): JoinHomeResponseDomainModel {
    return JoinHomeResponseDomainModel(
        name = this.name,
        imageId = this.imageId,
        createdAt = this.createdAt,
        members = this.members.map { it.toDomainModel() }
    )
}

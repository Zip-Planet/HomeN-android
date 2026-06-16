package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.Author
import com.devndev.homen.core.domain.model.home.Memo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMemoResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("author")
    val author: AuthorResponse,
    @SerialName("content")
    val content: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)

@Serializable
data class AuthorResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int
)

fun GetMemoResponse.toDomainModel(): Memo {
    return Memo(
        id = this.id,
        author = this.author.toDomainModel(),
        content = this.content,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun AuthorResponse.toDomainModel(): Author {
    return Author(
        uid = this.uid,
        name = this.name,
        profileImage = this.profileImage
    )
}

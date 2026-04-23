package com.devndev.homen.core.data.model.user.response

import com.devndev.homen.core.domain.model.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int,
    @SerialName("is_profile_set")
    val isProfileSet: Boolean,
    @SerialName("has_home")
    val hasHome: Boolean
)

fun UpdateProfileResponse.toDomainModel() = User(
    uid = this.uid,
    name = this.name,
    avatar = this.profileImage,
    hasHome = this.hasHome,
    isProfileSet = this.isProfileSet
)

package com.devndev.homen.core.domain.model.user

data class MyInfo(
    val uid: String,
    val name: String,
    val profileImage: Int?,
    val isProfileSet: Boolean,
    val hasHome: Boolean,
    val homeRole: Int?
)

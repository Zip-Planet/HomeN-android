package com.devndev.homen.core.domain.model.user

data class User(
    val uid: String,
    val name: String,
    val avatar: Int,
    val hasHome: Boolean, // manager 인지
    val isProfileSet: Boolean,
)
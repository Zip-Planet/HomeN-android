package com.devndev.homen.core.domain.model.user

data class Member(
    val name: String,
    val profileImage: Int,
    val role: RoleType,
)
package com.devndev.homen.core.domain.model.user

enum class RoleType(val role: Int) {
    MANAGER(role = 1),
    MEMBER(role = 2);

    companion object {
        fun fromId(id: Int): RoleType = RoleType.entries.find { it.role == id } ?: MEMBER
    }
}
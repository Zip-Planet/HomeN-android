package com.devndev.homen.core.domain.model.home

enum class AvatarType(val id: Int) {
    CHEF(1),
    WIZARD(2),
    HERO(3),
    GUARD(4),
    ZOMBIE(5),
    FARMER(6);

    companion object {
        fun fromId(id: Int): AvatarType = entries.find { it.id == id } ?: CHEF
    }
}

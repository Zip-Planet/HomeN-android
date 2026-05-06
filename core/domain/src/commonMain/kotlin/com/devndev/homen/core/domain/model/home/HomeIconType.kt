package com.devndev.homen.core.domain.model.home

enum class HomeIconType(val id: Int) {
    HOME1(1),
    HOME2(2),
    HOME3(3);

    companion object {
        fun fromId(id: Int): HomeIconType = entries.find { it.id == id } ?: HOME1
    }
}

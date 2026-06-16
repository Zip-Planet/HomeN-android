package com.devndev.homen.core.domain.model.home

data class Memo(
    val id: Int,
    val author: Author,
    val content: String,
    val createdAt: String,
    val updatedAt: String
)

data class Author(
    val uid: String,
    val name: String,
    val profileImage: Int
)


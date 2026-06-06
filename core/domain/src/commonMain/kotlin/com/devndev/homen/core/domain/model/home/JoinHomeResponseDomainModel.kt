package com.devndev.homen.core.domain.model.home

data class JoinHomeResponseDomainModel(
    val name: String,
    val imageId: Int,
    val createdAt: String,
    val members: List<Member>
)
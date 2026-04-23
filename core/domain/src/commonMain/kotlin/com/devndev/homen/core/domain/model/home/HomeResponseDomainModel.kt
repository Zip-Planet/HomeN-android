package com.devndev.homen.core.domain.model.home

data class HomeResponseDomainModel(
    val id: Int,
    val name: String,
    val image: Int,
    val inviteCode: String,
    val status: String,
    val createdAt: String
)
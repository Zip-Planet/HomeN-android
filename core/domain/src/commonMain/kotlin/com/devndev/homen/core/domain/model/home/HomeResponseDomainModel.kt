package com.devndev.homen.core.domain.model.home

data class HomeResponseDomainModel(
    val id: Int,
    val name: String,
    val image: Int,
    val inviteCode: String,
    val status: String,
    val createdAt: String,
    val members: List<Member>
)

data class Member(
    val name: String,
    val profileImage: Int?,
    val role: Int, // 1=관리자, 2=구성원
    val roleLabel: String
)
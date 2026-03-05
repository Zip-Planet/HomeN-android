package com.devndev.homen.core.domain.model.home

/**
 * 임시 화면 구성을 위한 사용자 모델
 */
data class User(
    val name: String,
    val avatar: Int, // 임시 리소스 인덱스 또는 ID
    val isManager: Boolean = false
)

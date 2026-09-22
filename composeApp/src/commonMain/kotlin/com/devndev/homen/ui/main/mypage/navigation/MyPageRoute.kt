package com.devndev.homen.ui.main.mypage.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MyPageRoute {
    @Serializable
    data object Setting: MyPageRoute
}
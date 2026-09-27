package com.devndev.homen.ui.main.mypage.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface MyPageRoute {
    @Serializable
    data object Setting: MyPageRoute

    @Serializable
    data class ProfileSetting(
        val nickname: String,
        val avatarId: Int
    ): MyPageRoute

    @Serializable
    data object HomeSetting: MyPageRoute
}
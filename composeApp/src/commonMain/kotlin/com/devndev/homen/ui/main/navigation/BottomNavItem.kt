package com.devndev.homen.ui.main.navigation

import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.home_icon
import homen.composeapp.generated.resources.my_icon
import homen.composeapp.generated.resources.present_icon
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

/**
 * 바텀 네비게이션 아이템 및 경로 정의
 */
@Serializable
sealed class BottomNavItem {
    abstract val title: String
    abstract val icon: DrawableResource

    @Serializable
    data object Home : BottomNavItem() {
        override val title: String = "홈"
        override val icon: DrawableResource = Res.drawable.home_icon
    }

    @Serializable
    data object Board : BottomNavItem() {
        override val title: String = "보드"
        override val icon: DrawableResource = Res.drawable.clipboard_icon
    }

    @Serializable
    data object Assignment : BottomNavItem() {
        override val title: String = "분담"
        override val icon: DrawableResource = Res.drawable.chart_icon
    }

    @Serializable
    data object Present : BottomNavItem() {
        override val title: String = "리워드"
        override val icon: DrawableResource = Res.drawable.present_icon
    }

    @Serializable
    data object MyPage : BottomNavItem() {
        override val title: String = "마이"
        override val icon: DrawableResource = Res.drawable.my_icon
    }
}

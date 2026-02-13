package com.devndev.homen.ui.main.navigation

import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chart_icon
import homen.composeapp.generated.resources.clipboard_icon
import homen.composeapp.generated.resources.home_icon
import homen.composeapp.generated.resources.my_icon
import homen.composeapp.generated.resources.present_icon
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val title: String,
    val route: String,
    val icon: DrawableResource
) {
    data object Home : BottomNavItem("홈", "home", Res.drawable.home_icon)
    data object Board : BottomNavItem("보드", "board", Res.drawable.clipboard_icon)
    data object Assignment : BottomNavItem("분담", "assignment", Res.drawable.chart_icon)
    data object Present: BottomNavItem("선물", "present", Res.drawable.present_icon)
    data object MyPage : BottomNavItem("마이", "mypage", Res.drawable.my_icon)
}

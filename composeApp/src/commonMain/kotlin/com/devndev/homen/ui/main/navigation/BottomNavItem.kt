package com.devndev.homen.ui.main.navigation

sealed class BottomNavItem(
    val title: String,
    val route: String
) {
    data object Home : BottomNavItem("Home", "home")
    data object List : BottomNavItem("List", "list")
    data object MyPage : BottomNavItem("My Page", "mypage")
}

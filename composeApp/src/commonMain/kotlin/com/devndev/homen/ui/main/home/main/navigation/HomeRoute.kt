package com.devndev.homen.ui.main.home.main.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed interface HomeRoute {
    @Serializable
    data object ChoreManage: HomeRoute
}
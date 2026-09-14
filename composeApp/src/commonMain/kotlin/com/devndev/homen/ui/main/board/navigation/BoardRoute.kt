package com.devndev.homen.ui.main.board.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BoardRoute {
    @Serializable
    data object BoardHelp: BoardRoute
}
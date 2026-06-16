package com.devndev.homen.ui.main.home.main.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed interface HomeRoute {
    @Serializable
    data object ChoreManage: HomeRoute

    @Serializable
    data object CreateChore: HomeRoute

    @Serializable
    data class EditChore(val choreId: Int): HomeRoute

    @Serializable
    data class ChoreDetail(val choreId: Int): HomeRoute

    @Serializable
    data class Memo(val choreId: Int, val memoId: Int?, val content: String?, val isEdit: Boolean): HomeRoute
}
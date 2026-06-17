package com.devndev.homen.ui.main.home.choredetail.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.chore.ChoreDifficulty
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.Memo
import com.devndev.homen.core.domain.model.home.WeeklyProgress

class ChoreDetailContract {
    sealed class Event: ViewEvent {
        data class OnInit(val id: Int): Event()
        data object OnBackClick: Event()
        data object OnDispose: Event()
        data class OnNavToMemo(val memoId: Int?, val content: String?, val isEdit: Boolean): Event()
        data class OnDeleteMemo(val memoId: Int): Event()
        data class OnUndoDeleteMemo(val memo: Memo, val index: Int): Event()
        data class OnDeleteConfirmMemo(val memoId: Int): Event()
        data object OnDeleteChore: Event()
        data object OnEditChore: Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val chore: Chore = Chore(
            id = 0,
            category = 0,
            name = "",
            description = "",
            repeatDays = emptyList(),
            difficulty = ChoreDifficulty.LOW
        ),
        val weeklyProgress: List<WeeklyProgress> = emptyList(),
        val memos: List<Memo> = emptyList()
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
        data class NavigateToMemo(val memoId: Int?, val content: String?, val isEdit: Boolean): Effect()
        data class ShowDeleteMemoSnackBar(val memo: Memo, val index: Int): Effect()
        data class NavigateToBackWithDelete(val choreId: Int): Effect()
        data object NavigateToEditChore: Effect()
    }
}
package com.devndev.homen.ui.main.home.memo.viewModel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState

class MemoContract {
    sealed class Event: ViewEvent {
        data class OnInitEdit(val content: String): Event()
        data class OnSaveClick(val isEdit: Boolean, val choreId: Int, val memoId: Int?): Event()
        data class OnValueChange(val content: String): Event()
        data object OnBackClick: Event()
    }

    data class State(
        val content: String = ""
    ): ViewState

    sealed class Effect: ViewSideEffect {
        data object NavigateToBack: Effect()
    }
}
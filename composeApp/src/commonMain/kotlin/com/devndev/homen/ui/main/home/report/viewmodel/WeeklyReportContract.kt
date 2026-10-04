package com.devndev.homen.ui.main.home.report.viewmodel

import com.devndev.homen.core.common.base.ViewEvent
import com.devndev.homen.core.common.base.ViewSideEffect
import com.devndev.homen.core.common.base.ViewState
import com.devndev.homen.core.domain.model.report.WeeklyReport

class WeeklyReportContract {
    sealed class Event : ViewEvent {
        data object OnInit : Event()
        data object OnEditChoresClick : Event()
        data object OnBackClick : Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val hasReport: Boolean = false,
        val weeklyReport: WeeklyReport? = null
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object PopBackStack : Effect()
        data object NavigateToChoreManage : Effect()
    }
}

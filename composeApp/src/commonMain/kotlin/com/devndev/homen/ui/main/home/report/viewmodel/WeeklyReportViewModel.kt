package com.devndev.homen.ui.main.home.report.viewmodel

import androidx.lifecycle.viewModelScope
import com.devndev.homen.core.common.base.BaseViewModel
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.usecase.home.GetWeeklyReportUseCase
import com.devndev.homen.util.DateUtil
import kotlinx.coroutines.launch

class WeeklyReportViewModel(
    private val getWeeklyReportUseCase: GetWeeklyReportUseCase
) : BaseViewModel<WeeklyReportContract.Event, WeeklyReportContract.State, WeeklyReportContract.Effect>() {

    override fun setInitialState() = WeeklyReportContract.State()

    override fun handleEvents(event: WeeklyReportContract.Event) {
        when (event) {
            WeeklyReportContract.Event.OnInit -> {
                fetchWeeklyReport()
            }

            WeeklyReportContract.Event.OnEditChoresClick -> {
                setEffect { WeeklyReportContract.Effect.NavigateToChoreManage }
            }

            WeeklyReportContract.Event.OnBackClick -> {
                setEffect { WeeklyReportContract.Effect.PopBackStack }
            }
        }
    }

    private fun fetchWeeklyReport() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            // 1. Check this week's report
            val thisWeekMonday = DateUtil.getThisWeekMonday()
            val thisWeekResult = getWeeklyReportUseCase(thisWeekMonday)

            if (thisWeekResult is ApiResult.Success && thisWeekResult.data.totalCount > 0) {
                setState {
                    copy(
                        hasReport = true,
                        weeklyReport = thisWeekResult.data,
                        isLoading = false
                    )
                }
                return@launch
            }

            // 2. Check last week's report if this week has no total chores
            val lastWeekMonday = DateUtil.getLastWeekMonday()
            val lastWeekResult = getWeeklyReportUseCase(lastWeekMonday)

            if (lastWeekResult is ApiResult.Success && lastWeekResult.data.totalCount > 0) {
                setState {
                    copy(
                        hasReport = true,
                        weeklyReport = lastWeekResult.data,
                        isLoading = false
                    )
                }
                return@launch
            }

            // 3. No report found
            setState {
                copy(
                    hasReport = false,
                    weeklyReport = null,
                    isLoading = false
                )
            }
        }
    }
}

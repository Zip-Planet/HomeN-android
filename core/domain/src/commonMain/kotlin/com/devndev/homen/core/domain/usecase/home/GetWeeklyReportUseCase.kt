package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.report.WeeklyReport
import com.devndev.homen.core.domain.repository.HomeRepository

class GetWeeklyReportUseCase(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(weekStart: String): ApiResult<WeeklyReport> {
        return homeRepository.getWeeklyReport(weekStart)
    }
}

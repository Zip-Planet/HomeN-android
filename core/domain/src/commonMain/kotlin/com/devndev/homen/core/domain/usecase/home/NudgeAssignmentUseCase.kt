package com.devndev.homen.core.domain.usecase.home

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.HomeRepository
import kotlin.time.Clock

sealed class NudgeResult {
    data object Success : NudgeResult()
    data object AlreadySent : NudgeResult()
    data class Error(val message: String?) : NudgeResult()
}

class NudgeAssignmentUseCase(
    private val homeRepository: HomeRepository
) {
    private companion object {
        const val ONE_HOUR_MILLIS = 60 * 60 * 1000L
    }

    suspend operator fun invoke(weekStart: String): NudgeResult {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val lastNudgeTime = homeRepository.getLastNudgeTime(weekStart)

        if (currentTime - lastNudgeTime < ONE_HOUR_MILLIS) {
            return NudgeResult.AlreadySent
        }

        return when (val apiResult = homeRepository.nudgeAssignment(weekStart)) {
            is ApiResult.Success -> {
                homeRepository.saveLastNudgeTime(weekStart, currentTime)
                NudgeResult.Success
            }
            is ApiResult.Error -> NudgeResult.Error(apiResult.message)
            ApiResult.NetworkError -> NudgeResult.Error(null)
        }
    }
}

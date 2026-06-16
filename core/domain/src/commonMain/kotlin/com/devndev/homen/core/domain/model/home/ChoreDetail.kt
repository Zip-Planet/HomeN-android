package com.devndev.homen.core.domain.model.home

data class ChoreDetail(
    val chore: Chore,
    val weeklyProgress: List<WeeklyProgress>
)

data class WeeklyProgress(
    val weekDay: Int,
    val label: String,
    val status: WeeklyProgressStatus,
    val completedBy: CompletedBy?
)

data class CompletedBy(
    val uid: String,
    val name: String,
    val profileImage: Int
)
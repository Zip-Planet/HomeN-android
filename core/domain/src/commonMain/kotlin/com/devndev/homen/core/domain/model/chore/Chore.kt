package com.devndev.homen.core.domain.model.chore

/**
 * 집안일
 */
data class Chore(
    val title: String,
    val description: String = "",
    val category: ChoreCategory,
    val days: Set<DayOfWeek>,
    val difficulty: ChoreDifficulty
)

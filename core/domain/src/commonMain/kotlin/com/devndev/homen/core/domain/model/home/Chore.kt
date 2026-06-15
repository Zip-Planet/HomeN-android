package com.devndev.homen.core.domain.model.home

import com.devndev.homen.core.domain.model.chore.ChoreDifficulty

data class Chore(
    val id: Int? = null,
    val category: Int,
    val name: String,
    val description: String = "",
    val repeatDays: List<Int>,
    val difficulty: ChoreDifficulty
)

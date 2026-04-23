package com.devndev.homen.core.domain.model.home

data class CreateHome(
    val name: String,
    val imageId: Int,
    val chores: List<Chore>,
    val rewards: List<Reward>
)

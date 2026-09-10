package com.devndev.homen.core.domain.model.board

enum class BotType(val type: String) {
    REWARD("reward_achieved"),
    ASSIGNMENT_CREATED("assignment_created"),
    ASSIGNMENT_CONFIRMED("assignment_confirmed")
}
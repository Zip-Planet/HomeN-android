package com.devndev.homen.core.domain.model.home

enum class WeeklyProgressStatus(val status: String) {
    COMPLETED("completed"),
    INCOMPLETE("incomplete"),
    NOT_SCHEDULED("not_scheduled");

    companion object {
        fun fromStatus(status: String): WeeklyProgressStatus = entries.find { it.status == status } ?: NOT_SCHEDULED
    }
}

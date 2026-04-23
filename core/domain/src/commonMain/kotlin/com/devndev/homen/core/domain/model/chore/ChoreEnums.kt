package com.devndev.homen.core.domain.model.chore

enum class ChoreCategory(val id: Int) {
    TRASH(1),
    CLEANING(2),
    BATHROOM(3),
    KITCHEN(4),
    LAUNDRY(5);

    companion object {
        fun fromId(id: Int) = entries.find { it.id == id } ?: TRASH
    }
}

enum class ChoreDifficulty(val id: Int, val point: Int) {
    LOW(1, 40),
    LOWER_MEDIUM(2, 80),
    MEDIUM(3, 120),
    UPPER_MEDIUM(4, 160),
    HIGH(5, 200);

    companion object {
        fun fromId(id: Int) = entries.find { it.id == id } ?: MEDIUM
    }
}

enum class RepeatDay(val value: Int) {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: MONDAY
    }
}

enum class StarterPackType {
    ROOMMATE,
    DORMITORY,
    MINIMAL
}

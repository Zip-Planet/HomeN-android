package com.devndev.homen.core.domain.model.chore

enum class ChoreCategory {
    TRASH,
    CLEANING,
    BATHROOM,
    KITCHEN,
    LAUNDRY
}

enum class ChoreDifficulty(val point: Int) {
    LOW(40),
    LOWER_MEDIUM(80),
    MEDIUM(120),
    UPPER_MEDIUM(160),
    HIGH(200)
}

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

enum class StarterPackType {
    ROOMMATE,
    DORMITORY,
    MINIMAL
}

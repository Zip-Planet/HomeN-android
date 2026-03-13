package com.devndev.homen.core.domain.model.chore

/**
 * 집안일 카테고리
 */
enum class ChoreCategory {
    TRASH,
    CLEANING,
    BATHROOM,
    KITCHEN,
    LAUNDRY
}

/**
 * 집안일 난이도
 */
enum class ChoreDifficulty {
    LOW,
    LOWER_MEDIUM,
    MEDIUM,
    UPPER_MEDIUM,
    HIGH
}

/**
 * 요일 정의
 */
enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

/**
 * 스타터팩 종류
 */
enum class StarterPackType {
    ROOMMATE,      // 룸메이트 기본팩
    DORMITORY,  // 기숙사 맞춤팩
    MINIMAL     // 미니멀 심플팩
}

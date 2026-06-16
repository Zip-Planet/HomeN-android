package com.devndev.homen.core.domain.model.chore

enum class ChoreCategory(val id: Int, val label: String) {
    TRASH(1, "쓰레기"),
    BATHROOM(2, "욕실"),
    CLEANING(3, "청소"),
    KITCHEN(4, "주방"),
    LAUNDRY(5, "세탁");

    companion object {
        fun fromId(id: Int) = entries.find { it.id == id } ?: TRASH
    }
}

enum class ChoreDifficulty(val id: Int, val point: Int, val label: String) {
    LOW(1, 40, "하"),
    LOWER_MEDIUM(2, 80, "중하"),
    MEDIUM(3, 120, "중"),
    UPPER_MEDIUM(4, 160, "중상"),
    HIGH(5, 200, "상");

    companion object {
        fun fromId(id: Int) = entries.find { it.id == id } ?: MEDIUM
    }
}

enum class RepeatDay(val value: Int, val day: String) {
    MONDAY(0, "월"),
    TUESDAY(1, "화"),
    WEDNESDAY(2, "수"),
    THURSDAY(3, "목"),
    FRIDAY(4, "금"),
    SATURDAY(5, "토"),
    SUNDAY(6, "일");

    companion object {
        fun fromValue(value: Int) = entries.find { it.value == value } ?: MONDAY
    }
}

enum class StarterPackType(val id: Int) {
    ROOMMATE(id = 0),
    DORMITORY(id = 1),
    MINIMAL(id = 2);

    companion object {
        fun fromValue(id: Int) = entries.find { it.id == id }
    }
}

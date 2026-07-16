package com.devndev.homen.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.time.Clock

object DateUtil {
    fun formatIsoDate(isoString: String): String {
        return try {
            val datePart = isoString.split("T")[0]
            val parts = datePart.split("-")

            val year = parts[0]
            val month = parts[1].toInt() // "05" -> 5
            val day = parts[2].toInt()   // "27" -> 27

            "${year}년 ${month}월 ${day}일"
        } catch (e: Exception) {
            isoString // 파싱 실패 시 원본 반환
        }
    }

    /**
     * 현재 시간을 기준으로 특정 주차의 월요일 날짜를 반환합니다. (YYYY-MM-DD)
     * @param weeksOffset 주차 오프셋 (0: 이번 주, 1: 다음 주, -1: 지난 주, -2: 2주 전)
     */
    fun getMondayOfWeek(weeksOffset: Int = 0): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        // 해당 주차로 이동
        val targetDate = if (weeksOffset >= 0) {
            now.plus(weeksOffset, DateTimeUnit.WEEK)
        } else {
            now.minus(-weeksOffset, DateTimeUnit.WEEK)
        }

        // 해당 날짜가 속한 주의 월요일 구하기
        // isoDayNumber: Mon(1) ~ Sun(7)
        val daysToMinus = targetDate.dayOfWeek.ordinal // ordinal은 Mon(0) ~ Sun(6)
        val monday = targetDate.minus(daysToMinus, DateTimeUnit.DAY)

        return monday.toString()
    }

    fun getThisWeekMonday(): String = getMondayOfWeek(0)
    fun getNextWeekMonday(): String = getMondayOfWeek(1)
    fun getLastWeekMonday(): String = getMondayOfWeek(-1)
    fun getTwoWeeksAgoMonday(): String = getMondayOfWeek(-2)
}
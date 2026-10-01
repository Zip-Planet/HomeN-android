package com.devndev.homen.util

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.minus
import kotlinx.datetime.number
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
     * "YYYY-MM-DD" 형식을 "YYYY년 N월 N주차" 형식으로 변환합니다.
     * 해당 주의 목요일(Thursday)이 속한 달을 기준으로 주차를 계산합니다. (ISO-8601 방식)
     */
    fun formatWeekOfMonth(dateString: String): String {
        return try {
            val datePart = dateString.split("T")[0]
            val date = LocalDate.parse(datePart)

            // 해당 날짜가 속한 주의 목요일을 찾습니다.
            val dayOfWeek = date.dayOfWeek.ordinal // MONDAY(0) ~ SUNDAY(6)
            val daysToThursday = 3 - dayOfWeek
            val thursday = date.plus(daysToThursday, DateTimeUnit.DAY)

            val year = thursday.year
            val month = thursday.month.number

            // 목요일이 해당 월의 몇 번째 주인지 계산합니다.
            val weekOfMonth = (thursday.dayOfMonth - 1) / 7 + 1

            "${year}년 ${month}월 ${weekOfMonth}주차"
        } catch (e: Exception) {
            dateString
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

    /**
     * ISO 8601 일시 문자열(예: "2026-09-28T11:24:58.212350Z")을
     * 현재 시간 대비 상대 시간 문자열("방금", "1분 전"~"59분 전", "1시간 전"~"23시간 전", "1일 전" 등)로 변환합니다.
     */
    fun formatRelativeTime(isoString: String): String {
        if (isoString.isBlank()) return "방금"
        return try {
            val sanitizedIso = if (!isoString.endsWith("Z") && !isoString.contains("+")) {
                "${isoString}Z"
            } else {
                isoString
            }
            val createdInstant = Instant.parse(sanitizedIso)
            val nowInstant = Clock.System.now()
            val duration = nowInstant - createdInstant

            val seconds = duration.inWholeSeconds
            val minutes = duration.inWholeMinutes
            val hours = duration.inWholeHours
            val days = duration.inWholeDays

            when {
                seconds < 60 -> "방금"
                minutes < 60 -> "${minutes}분 전"
                hours < 24 -> "${hours}시간 전"
                else -> "${days}일 전"
            }
        } catch (e: Exception) {
            "방금"
        }
    }
}
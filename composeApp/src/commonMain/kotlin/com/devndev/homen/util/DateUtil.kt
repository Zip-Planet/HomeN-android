package com.devndev.homen.util

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
}
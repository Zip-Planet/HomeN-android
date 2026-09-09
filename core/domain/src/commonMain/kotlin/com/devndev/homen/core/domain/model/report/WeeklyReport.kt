package com.devndev.homen.core.domain.model.report

data class WeeklyReport(
    val id: Int,
    val totalCount: Int,
    val completedCount: Int,
    val progressRate: Int,
    val mvp: WeeklyReportMember?,
    val memberStats: List<WeeklyReportMemberStat>,
    val mostDone: WeeklyReportHighlight?,
    val mostMissed: WeeklyReportHighlight?
)

data class WeeklyReportMember(
    val uid: String,
    val name: String,
    val profileImage: Int?,
    val point: Int?,
    val completedCount: Int?
)

data class WeeklyReportMemberStat(
    val uid: String,
    val name: String,
    val profileImage: Int?,
    val assignedCount: Int,
    val completedCount: Int,
    val point: Int
)

data class WeeklyReportHighlight(
    val name: String,
    val count: Int
)

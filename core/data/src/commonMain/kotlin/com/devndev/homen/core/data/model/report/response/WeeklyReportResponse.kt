package com.devndev.homen.core.data.model.report.response

import com.devndev.homen.core.domain.model.report.WeeklyReport
import com.devndev.homen.core.domain.model.report.WeeklyReportHighlight
import com.devndev.homen.core.domain.model.report.WeeklyReportMember
import com.devndev.homen.core.domain.model.report.WeeklyReportMemberStat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyReportResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("week_start")
    val weekStart: String,
    @SerialName("total_count")
    val totalCount: Int,
    @SerialName("completed_count")
    val completedCount: Int,
    @SerialName("progress_rate")
    val progressRate: Int,
    @SerialName("mvp")
    val mvp: WeeklyReportMemberResponse?,
    @SerialName("member_stats")
    val memberStats: List<WeeklyReportMemberStatResponse>,
    @SerialName("most_done")
    val mostDone: WeeklyReportHighlightResponse?,
    @SerialName("most_missed")
    val mostMissed: WeeklyReportHighlightResponse?,
    @SerialName("generated_at")
    val generatedAt: String
)

@Serializable
data class WeeklyReportMemberResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?,
    @SerialName("point")
    val point: Int? = null,
    @SerialName("completed_count")
    val completedCount: Int? = null
)

@Serializable
data class WeeklyReportMemberStatResponse(
    @SerialName("uid")
    val uid: String,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profileImage: Int?,
    @SerialName("assigned_count")
    val assignedCount: Int,
    @SerialName("completed_count")
    val completedCount: Int,
    @SerialName("point")
    val point: Int
)

@Serializable
data class WeeklyReportHighlightResponse(
    @SerialName("name")
    val name: String,
    @SerialName("count")
    val count: Int
)

fun WeeklyReportResponse.toDomainModel(): WeeklyReport {
    return WeeklyReport(
        id = id,
        weekStart = weekStart,
        totalCount = totalCount,
        completedCount = completedCount,
        progressRate = progressRate,
        mvp = mvp?.toDomainModel(),
        memberStats = memberStats.map { it.toDomainModel() },
        mostDone = mostDone?.toDomainModel(),
        mostMissed = mostMissed?.toDomainModel(),
        generatedAt = generatedAt
    )
}

fun WeeklyReportMemberResponse.toDomainModel(): WeeklyReportMember {
    return WeeklyReportMember(
        uid = uid,
        name = name,
        profileImage = profileImage,
        point = point,
        completedCount = completedCount
    )
}

fun WeeklyReportMemberStatResponse.toDomainModel(): WeeklyReportMemberStat {
    return WeeklyReportMemberStat(
        uid = uid,
        name = name,
        profileImage = profileImage,
        assignedCount = assignedCount,
        completedCount = completedCount,
        point = point
    )
}

fun WeeklyReportHighlightResponse.toDomainModel(): WeeklyReportHighlight {
    return WeeklyReportHighlight(
        name = name,
        count = count
    )
}

package com.devndev.homen.core.domain.model.home

data class Assignment(
    val id: Int,
    val weekStart: String,
    val status: String,
    val generatedAt: String,
    val confirmedAt: String?,
    val items: List<AssignmentItem>,
    val memberPoints: List<MemberPoint>
)

data class AssignmentItem(
    val id: Int,
    val homeChoreId: Int?,
    val weekday: Int,
    val weekdayLabel: String,
    val choreName: String,
    val category: Int,
    val categoryLabel: String,
    val difficulty: Int,
    val difficultyLabel: String,
    val point: Int,
    val assignee: AssignmentAssignee?,
    val date: String,
    val isCompleted: Boolean
)

data class AssignmentAssignee(
    val uid: String,
    val name: String,
    val profileImage: Int?
)

data class MemberPoint(
    val uid: String,
    val name: String,
    val profileImage: Int?,
    val expectedPoint: Int
)

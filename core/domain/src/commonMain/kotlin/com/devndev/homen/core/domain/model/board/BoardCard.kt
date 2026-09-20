package com.devndev.homen.core.domain.model.board

data class BoardCard(
    val type: String, // bot, help, swap
    val id: Int,
    val weekStart: String,
    val createdAt: String,
    // Bot specific
    val kind: String? = null,
    val kindLabel: String? = null,
    val payload: BoardPayload? = null,
    // Help / Swap specific
    val status: String? = null,
    val message: String? = null,
    val requester: BoardMember? = null,
    val acceptedBy: BoardMember? = null,
    val respondedBy: BoardMember? = null,
    val item: BoardChoreItem? = null,
    val requesterItem: BoardChoreItem? = null,
    val targetItem: BoardChoreItem? = null
)

sealed interface BoardPayload {
    data class Reward(
        val claimedBy: BoardMember,
        val goalPoint: Int,
        val rewardName: String,
        val claimedByPoint: Int
    ) : BoardPayload

    data class Assignment(
        val weekStart: String,
        val totalCount: Int
    ) : BoardPayload

    data class Report(
        val mvpName: String,
        val mvpPoint: Int,
        val totalChoreCount: Int,
        val completedChoreCount: Int,
        val completionRate: Double
    ) : BoardPayload

    data class Default(
        val content: String? = null
    ) : BoardPayload
}

data class BoardMember(
    val uid: String,
    val name: String,
    val profileImage: Int?
)

data class BoardChoreItem(
    val id: Int,
    val choreName: String,
    val weekday: Int,
    val weekdayLabel: String,
    val difficulty: Int,
    val point: Int,
    val date: String,
    val assignee: BoardMember?
)

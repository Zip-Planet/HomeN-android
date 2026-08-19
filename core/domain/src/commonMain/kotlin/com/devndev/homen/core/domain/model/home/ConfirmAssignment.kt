package com.devndev.homen.core.domain.model.home

data class ConfirmAssignment(
    val confirmed: Boolean,
    val needsRegenerate: Boolean,
    val hasChanges: Boolean,
    val addedCount: Int,
    val updatedCount: Int,
    val removedCount: Int,
    val blockedReason: String?,
    val assignment: Assignment?
)

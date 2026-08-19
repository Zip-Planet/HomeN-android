package com.devndev.homen.core.data.model.home.response

import com.devndev.homen.core.domain.model.home.ConfirmAssignment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmAssignmentResponse(
    @SerialName("confirmed")
    val confirmed: Boolean,
    @SerialName("needs_regenerate")
    val needsRegenerate: Boolean,
    @SerialName("has_changes")
    val hasChanges: Boolean,
    @SerialName("added_count")
    val addedCount: Int,
    @SerialName("updated_count")
    val updatedCount: Int,
    @SerialName("removed_count")
    val removedCount: Int,
    @SerialName("blocked_reason")
    val blockedReason: String?,
    @SerialName("assignment")
    val assignment: GetAssignmentResponse?
)

fun ConfirmAssignmentResponse.toDomainModel(): ConfirmAssignment {
    return ConfirmAssignment(
        confirmed = confirmed,
        needsRegenerate = needsRegenerate,
        hasChanges = hasChanges,
        addedCount = addedCount,
        updatedCount = updatedCount,
        removedCount = removedCount,
        blockedReason = blockedReason,
        assignment = assignment?.toDomainModel()
    )
}

package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.ChoreDetail
import com.devndev.homen.core.domain.model.home.ConfirmAssignment
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.JoinHomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.Memo

interface HomeRepository {
    suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel>
    suspend fun getHome(): ApiResult<HomeResponseDomainModel>
    suspend fun getHasHome(): ApiResult<Boolean>
    suspend fun getJoinHome(code: String): ApiResult<JoinHomeResponseDomainModel>
    suspend fun joinHome(code: String): ApiResult<Unit>
    suspend fun createChore(chores: List<Chore>): ApiResult<Unit>
    suspend fun getChores(): ApiResult<List<Chore>>
    suspend fun deleteChore(id: Int): ApiResult<Unit>
    suspend fun getChoreDetail(id: Int): ApiResult<ChoreDetail>
    suspend fun editChore(chore: Chore): ApiResult<Unit>
    suspend fun getAssignments(weekStart: String?): ApiResult<Assignment>
    suspend fun createAssignment(weekStart: String?): ApiResult<Assignment>
    suspend fun confirmAssignment(assignmentId: Int, acknowledged: Boolean): ApiResult<ConfirmAssignment>
    suspend fun regenerateAssignment(assignmentId: Int): ApiResult<Assignment>
    suspend fun getMemos(id: Int): ApiResult<List<Memo>>
    suspend fun createMemos(id: Int, content: String): ApiResult<Unit>
    suspend fun editMemo(choreId: Int, memoId: Int, content: String): ApiResult<Unit>
    suspend fun deleteMemo(choreId: Int, memoId: Int): ApiResult<Unit>

    suspend fun completeChore(homeChoreId: Int, date: String?): ApiResult<Unit>
    suspend fun cancelCompleteChore(homeChoreId: Int, completionDate: String): ApiResult<Unit>
}

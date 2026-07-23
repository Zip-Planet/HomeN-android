package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.home.request.CreateAssignmentRequest
import com.devndev.homen.core.data.model.home.request.CreateChoreRequest
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.JoinHomeRequest
import com.devndev.homen.core.data.model.home.request.MemoRequest
import com.devndev.homen.core.data.model.home.request.toDataModel
import com.devndev.homen.core.data.model.home.request.toEditDataModel
import com.devndev.homen.core.data.model.home.response.toDomainModel
import com.devndev.homen.core.data.service.home.HomeService
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Assignment
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.ChoreDetail
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.JoinHomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.Memo
import com.devndev.homen.core.domain.repository.HomeRepository
import io.ktor.client.plugins.ResponseException

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {
    override suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel> {
        return try {
            val response = homeService.createHome(
                CreateHomeRequest(
                    name = createHome.name,
                    imageId = createHome.imageId,
                    chores = createHome.chores.map { it.toDataModel() },
                    rewards = createHome.rewards.map { it.toDataModel() }
                )
            )
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getHome(): ApiResult<HomeResponseDomainModel> {
        return try {
            val response = homeService.getHome()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getHasHome(): ApiResult<Boolean> {
        return try {
            val response = homeService.getHasHome()
            ApiResult.Success(response.hasHome)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getJoinHome(code: String): ApiResult<JoinHomeResponseDomainModel> {
        return try {
            val response = homeService.getJoinHome(code)
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun joinHome(code: String): ApiResult<Unit> {
        return try {
            val response = homeService.joinHome(JoinHomeRequest(code))
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun createChore(chores: List<Chore>): ApiResult<Unit> {
        return try {
            val response =
                homeService.createChore(CreateChoreRequest(chores = chores.map { it.toDataModel() }))
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getChores(): ApiResult<List<Chore>> {
        return try {
            val response = homeService.getChores()
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun deleteChore(id: Int): ApiResult<Unit> {
        return try {
            val response = homeService.deleteChore(id)
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getChoreDetail(id: Int): ApiResult<ChoreDetail> {
        return try {
            val response = homeService.getChoreDetail(id)
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun editChore(chore: Chore): ApiResult<Unit> {
        return try {
            val response = homeService.editChore(chore.id!!, chore.toEditDataModel())
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getAssignments(weekStart: String?): ApiResult<Assignment> {
        return try {
            val response = homeService.getAssignments(weekStart)
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun createAssignment(weekStart: String?): ApiResult<Assignment> {
        return try {
            val response = homeService.createAssignment(CreateAssignmentRequest(weekStart))
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun confirmAssignment(assignmentId: Int): ApiResult<Assignment> {
        return try {
            val response = homeService.confirmAssignment(assignmentId)
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun getMemos(id: Int): ApiResult<List<Memo>> {
        return try {
            val response = homeService.getMemos(id)
            ApiResult.Success(response.map { it.toDomainModel() })
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun createMemos(
        id: Int,
        content: String
    ): ApiResult<Unit> {
        return try {
            val response = homeService.createMemo(id = id, createMemoRequest = MemoRequest(content))
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun editMemo(
        choreId: Int,
        memoId: Int,
        content: String
    ): ApiResult<Unit> {
        return try {
            val response = homeService.editMemo(
                choreId = choreId,
                memoId = memoId,
                editMemoRequest = MemoRequest(content)
            )
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun deleteMemo(
        choreId: Int,
        memoId: Int
    ): ApiResult<Unit> {
        return try {
            val response = homeService.deleteMemo(
                choreId = choreId,
                memoId = memoId
            )
            ApiResult.Success(response)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}
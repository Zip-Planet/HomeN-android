package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.data.model.home.request.CreateChoreRequest
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.EditChoreRequest
import com.devndev.homen.core.data.model.home.request.JoinHomeRequest
import com.devndev.homen.core.data.model.home.request.MemoRequest
import com.devndev.homen.core.data.model.home.response.ChoreDetailResponse
import com.devndev.homen.core.data.model.home.response.ChoreResponse
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHasHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse
import com.devndev.homen.core.data.model.home.response.GetMemoResponse
import com.devndev.homen.core.data.model.home.response.JoinHomeResponse

interface HomeService {
    companion object {
        const val CREATE_HOME = "/homes/"
        const val GET_HOME = "/homes/mine/"
        const val GET_HAS_HOME = "/homes/mine/membership/"
        const val GET_JOIN_HOME = "/homes/invite/"
        const val JOIN_HOME = "/homes/join/"

        const val CHORES = "/homes/mine/chores/"
    }

    suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse
    suspend fun getHome(): GetHomeResponse
    suspend fun getHasHome(): GetHasHomeResponse
    suspend fun getJoinHome(code: String): JoinHomeResponse
    suspend fun joinHome(joinHomeRequest: JoinHomeRequest)
    suspend fun createChore(createChoreRequest: CreateChoreRequest)
    suspend fun getChores(): List<ChoreResponse>
    suspend fun deleteChore(id: Int)
    suspend fun getChoreDetail(id: Int): ChoreDetailResponse
    suspend fun editChore(id: Int, editChoreRequest: EditChoreRequest)

    suspend fun getMemos(id: Int): List<GetMemoResponse>

    suspend fun createMemo(id: Int, createMemoRequest: MemoRequest)
    suspend fun editMemo(choreId: Int, memoId: Int, editMemoRequest: MemoRequest)
}
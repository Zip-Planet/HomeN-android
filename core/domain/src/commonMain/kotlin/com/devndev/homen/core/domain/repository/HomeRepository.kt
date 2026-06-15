package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.Chore
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.JoinHomeResponseDomainModel

interface HomeRepository {
    suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel>
    suspend fun getHome(): ApiResult<HomeResponseDomainModel>
    suspend fun getHasHome(): ApiResult<Boolean>
    suspend fun getJoinHome(code: String): ApiResult<JoinHomeResponseDomainModel>
    suspend fun joinHome(code: String): ApiResult<Unit>
    suspend fun createChore(chores: List<Chore>): ApiResult<Unit>
    suspend fun getChores(): ApiResult<List<Chore>>
    suspend fun deleteChore(id: Int): ApiResult<Unit>
    suspend fun getHomeDetail(id: Int): ApiResult<Chore>
    suspend fun editChore(chore: Chore): ApiResult<Unit>
}
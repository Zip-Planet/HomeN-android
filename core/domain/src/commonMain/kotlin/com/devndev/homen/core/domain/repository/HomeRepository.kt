package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel
import com.devndev.homen.core.domain.model.home.JoinHomeResponseDomainModel

interface HomeRepository {
    suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel>
    suspend fun getHome(): ApiResult<HomeResponseDomainModel>
    suspend fun getHasHome(): ApiResult<Boolean>
    suspend fun getJoinHome(code: String): ApiResult<JoinHomeResponseDomainModel>
    suspend fun joinHome(code: String): ApiResult<Unit>
}
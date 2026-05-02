package com.devndev.homen.core.domain.repository

import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.model.home.CreateHome
import com.devndev.homen.core.domain.model.home.HomeResponseDomainModel

interface HomeRepository {
    suspend fun createHome(createHome: CreateHome): ApiResult<HomeResponseDomainModel>
    suspend fun getHome(): ApiResult<HomeResponseDomainModel>
    suspend fun getHasHome(): ApiResult<Boolean>
}
package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.request.JoinHomeRequest
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHasHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse
import com.devndev.homen.core.data.model.home.response.JoinHomeResponse

interface HomeService {
    companion object {
        const val CREATE_HOME = "/homes/"
        const val GET_HOME = "/homes/mine/"
        const val GET_HAS_HOME = "/homes/mine/membership/"
        const val GET_JOIN_HOME = "/homes/invite/"
        const val JOIN_HOME = "/homes/join/"
    }

    suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse
    suspend fun getHome(): GetHomeResponse
    suspend fun getHasHome(): GetHasHomeResponse
    suspend fun getJoinHome(code: String): JoinHomeResponse
    suspend fun joinHome(joinHomeRequest: JoinHomeRequest)
}
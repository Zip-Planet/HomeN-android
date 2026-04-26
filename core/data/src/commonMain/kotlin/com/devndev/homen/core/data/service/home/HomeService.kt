package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import com.devndev.homen.core.data.model.home.response.GetHomeResponse

interface HomeService {
    companion object {
        const val CREATE_HOME = "/homes/"
        const val GET_HOME = "/homes/mine/"
    }

    suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse
    suspend fun getHome(): GetHomeResponse
}
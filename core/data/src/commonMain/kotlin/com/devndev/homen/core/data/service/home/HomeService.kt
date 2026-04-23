package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse

interface HomeService {
    companion object {
        const val CREATE_HOME = "/homes/"
    }

    suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse
}
package com.devndev.homen.core.data.service.home

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.home.request.CreateHomeRequest
import com.devndev.homen.core.data.model.home.response.CreateHomeResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

class HomeServiceImpl(
    private val client: HttpClient
): HomeService {
    override suspend fun createHome(createHomeRequest: CreateHomeRequest): CreateHomeResponse {
        return client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += HomeService.CREATE_HOME
            }
            contentType(ContentType.Application.Json)
            setBody(createHomeRequest)
        }.body()
    }
}
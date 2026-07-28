package com.devndev.homen.core.data.service.reward

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.reward.request.RewardRequest
import com.devndev.homen.core.data.model.reward.response.RewardListResponse
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.flow.first

class RewardServiceImpl(
    private val client: HttpClient,
    private val tokenRepository: TokenRepository
) : RewardService {
    override suspend fun getRewards(): RewardListResponse {
        val accessToken = tokenRepository.getAccessToken().first()
        return client.get {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += RewardService.REWARDS
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
        }.body()
    }

    override suspend fun createReward(rewardRequest: RewardRequest) {
        val accessToken = tokenRepository.getAccessToken().first()
        client.post {
            url {
                takeFrom(Config.BASE_URL)
                encodedPath += RewardService.REWARDS
            }
            contentType(ContentType.Application.Json)
            accessToken?.let {
                header(HttpHeaders.Authorization, "Bearer $it")
            }
            setBody(rewardRequest)
        }
    }
}

package com.devndev.homen.core.data.di

import com.devndev.homen.core.common.Config
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * API 통신(Ktor 등) 관련 의존성
 */
val networkModule = module {
    single {
        HttpClient {
            // 1. 타임아웃 설정 (서버 연결 안정성 확보)
            install(HttpTimeout) {
                requestTimeoutMillis = Config.REQUEST_TIMEOUT
                connectTimeoutMillis = Config.CONNECT_TIMEOUT
                socketTimeoutMillis = Config.SOCKET_TIMEOUT
            }

            // 2. JSON 직렬화 설정 (유연한 파싱)
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            // 3. 자동 인증 및 토큰 갱신 로직 (추후 연동)
            install(Auth) {
                bearer {
                    loadTokens { null }
                    refreshTokens { null }
                    sendWithoutRequest { request ->
                        val publicUrls = listOf("login", "register", "auth/refresh", "/auth/kakao/")
                        publicUrls.any { request.url.encodedPath.endsWith(it) }
                    }
                }
            }

            // 4. HTTP 로깅 설정
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            // 5. 공통 요청 설정 (Base URL 적용)
            defaultRequest {
                url(Config.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}

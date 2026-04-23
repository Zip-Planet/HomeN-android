package com.devndev.homen.core.data.di

import com.devndev.homen.core.common.Config
import com.devndev.homen.core.data.model.auth.request.TokenRefreshRequest
import com.devndev.homen.core.data.service.auth.AuthService
import com.devndev.homen.core.domain.repository.TokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
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
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * API 통신(Ktor 등) 관련 의존성
 */
val networkModule = module {
    single {
        val tokenRepository: TokenRepository = get()
        
        HttpClient {
            // 1. 타임아웃 설정
            install(HttpTimeout) {
                requestTimeoutMillis = Config.REQUEST_TIMEOUT
                connectTimeoutMillis = Config.CONNECT_TIMEOUT
                socketTimeoutMillis = Config.SOCKET_TIMEOUT
            }

            // 2. JSON 직렬화 설정
            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            // 3. 자동 인증 로직
            install(Auth) {
                bearer {
                    loadTokens {
                        val access = tokenRepository.getAccessToken().first<String?>()
                        val refresh = tokenRepository.getRefreshToken().first<String?>()
                        
                        if (access != null && refresh != null) {
                            BearerTokens(access, refresh)
                        } else {
                            null
                        }
                    }
                    
                    refreshTokens {
                        val currentRefreshToken = tokenRepository.getRefreshToken().first<String?>() ?: return@refreshTokens null
                        
                        try {
                            // 토큰 갱신을 위해 별도의 서비스 인스턴스 사용 (무한 루프 방지)
                            val authService: AuthService = get()
                            val response = authService.refreshToken(
                                TokenRefreshRequest(
                                    currentRefreshToken
                                )
                            )
                            
                            // 갱신된 액세스 토큰 저장
                            tokenRepository.saveTokens(
                                accessToken = response.access,
                                refreshToken = currentRefreshToken // 기존 리프레시 토큰 유지 (서버 스펙에 따라 다름)
                            )
                            
                            BearerTokens(
                                accessToken = response.access,
                                refreshToken = currentRefreshToken
                            )
                        } catch (e: Exception) {
                            // 갱신 실패 시 토큰 삭제 (로그아웃 처리 등)
                            tokenRepository.clearTokens()
                            null
                        }
                    }
                    
                    sendWithoutRequest { request ->
                        // 인증이 필요 없는 URL 패턴 정의
                        val publicUrls = listOf(AuthService.KAKAO_LOGIN, AuthService.TOKEN_REFRESH)
                        publicUrls.any { request.url.encodedPath.contains(it) }
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

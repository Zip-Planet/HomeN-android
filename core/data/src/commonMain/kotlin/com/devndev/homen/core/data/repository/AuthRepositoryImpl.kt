package com.devndev.homen.core.data.repository

import com.devndev.homen.core.data.model.auth.request.KakaoLoginRequest
import com.devndev.homen.core.data.model.auth.request.LogoutRequest
import com.devndev.homen.core.data.model.auth.response.toDomainModel
import com.devndev.homen.core.data.service.auth.AuthService
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.model.auth.AuthToken
import com.devndev.homen.core.domain.model.auth.SocialToken
import com.devndev.homen.core.domain.model.common.ApiResult
import com.devndev.homen.core.domain.repository.AuthRepository
import io.ktor.client.plugins.ResponseException

class AuthRepositoryImpl(
    private val kakaoAuthenticator: SocialAuthenticator<KakaoUser>,
    private val appleAuthenticator: SocialAuthenticator<AppleUser>,
    private val authService: AuthService
) : AuthRepository {
    override suspend fun loginWithKakao(): SocialAuthResult<KakaoUser> {
        return kakaoAuthenticator.authenticate()
    }

    override suspend fun loginWithApple(): SocialAuthResult<AppleUser> {
        return appleAuthenticator.authenticate()
    }

    override suspend fun kakaoLoginToServer(socialToken: SocialToken): ApiResult<AuthToken> {
        return try {
            val response = authService.kakaoLogin(KakaoLoginRequest(socialToken.token))
            ApiResult.Success(response.toDomainModel())
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }

    override suspend fun logout(refreshToken: String): ApiResult<Unit> {
        return try {
            authService.logout(LogoutRequest(refreshToken))
            ApiResult.Success(Unit)
        } catch (e: ResponseException) {
            ApiResult.Error(code = e.response.status.value, message = e.message)
        } catch (e: Exception) {
            ApiResult.NetworkError
        }
    }
}

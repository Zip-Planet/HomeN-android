package com.devndev.homen.core.data.auth

import android.content.Context
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoAuthenticatorImpl(private val context: Context) : SocialAuthenticator<KakaoUser> {
    override suspend fun authenticate(): SocialAuthResult<KakaoUser> = suspendCancellableCoroutine { continuation ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    continuation.resume(SocialAuthResult.UserCancelled)
                } else {
                    continuation.resume(SocialAuthResult.Error)
                }
            } else if (token != null) {
                continuation.resume(SocialAuthResult.Success(KakaoUser(token.accessToken)))
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}

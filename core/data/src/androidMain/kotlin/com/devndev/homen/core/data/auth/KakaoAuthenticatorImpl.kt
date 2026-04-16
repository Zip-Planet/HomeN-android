package com.devndev.homen.core.data.auth

import android.content.Context
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.kakao.sdk.auth.AuthCodeClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoAuthenticatorImpl(private val context: Context) : SocialAuthenticator<KakaoUser> {
    override suspend fun authenticate(): SocialAuthResult<KakaoUser> = suspendCancellableCoroutine { continuation ->

        // 인가 코드 응답을 처리하는 콜백
        val callback: (String?, Throwable?) -> Unit = { code, error ->
            if (error != null) {
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    continuation.resume(SocialAuthResult.UserCancelled)
                } else {
                    continuation.resume(SocialAuthResult.Error)
                }
            } else if (code != null) {
                // 여기서 code가 인가 코드(Authorization Code)입니다.
                // KakaoUser 모델의 accessToken 자리에 code를 담아서 보냅니다.
                continuation.resume(SocialAuthResult.Success(KakaoUser(accessToken = code)))
            }
        }

        // AuthCodeClient를 사용하여 인가 코드만 요청
        if (AuthCodeClient.instance.isKakaoTalkLoginAvailable(context)) {
            AuthCodeClient.instance.authorizeWithKakaoTalk(context, callback = callback)
        } else {
            AuthCodeClient.instance.authorizeWithKakaoAccount(context, callback = callback)
        }
    }
//    override suspend fun authenticate(): SocialAuthResult<KakaoUser> = suspendCancellableCoroutine { continuation ->
//        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
//            if (error != null) {
//                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                    continuation.resume(SocialAuthResult.UserCancelled)
//                } else {
//                    continuation.resume(SocialAuthResult.Error)
//                }
//            } else if (token != null) {
//                continuation.resume(SocialAuthResult.Success(KakaoUser(token.accessToken)))
//            }
//        }
//
//        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
//            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
//        } else {
//            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
//        }
//    }
}

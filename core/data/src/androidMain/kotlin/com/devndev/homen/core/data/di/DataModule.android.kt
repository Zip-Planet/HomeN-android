package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.auth.KakaoAuthenticatorImpl
import com.devndev.homen.core.domain.auth.SocialAuthResult
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * 안드로이드 전용 데이터 모듈 구현
 */
actual val dataModule: Module = module {
    //  =공통 모듈 포함
    includes(commonDataModule)

    single<SocialAuthenticator<KakaoUser>>(named("kakao")) { KakaoAuthenticatorImpl(get()) }

    // 안드로이드는 애플 로그인을 사용하지 않으므로 가짜 객체 등록 (이름: apple)
    single<SocialAuthenticator<AppleUser>>(named("apple")) {
        object : SocialAuthenticator<AppleUser> {
            override suspend fun authenticate(): SocialAuthResult<AppleUser> = SocialAuthResult.Error
        }
    }
}

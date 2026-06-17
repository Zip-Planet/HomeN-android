package com.devndev.homen.di

import com.devndev.homen.core.data.di.dataModule
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.AppleUser
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * 공통 Koin 초기화 함수
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        dataModule,
        domainModule,
        viewModelModule,
        managerModule
    )
}

/**
 * iOS 전용 초기화 브릿지
 */
fun doInitKoin(
    kakaoAuthenticator: SocialAuthenticator<KakaoUser>,
    appleAuthenticator: SocialAuthenticator<AppleUser>,
    onKakaoShare: (String, String) -> Unit
) = initKoin {
    modules(module {
        single(named("kakao")) { kakaoAuthenticator }
        single(named("apple")) { appleAuthenticator }
        single(named("onKakaoShare")) { onKakaoShare }
    })
}

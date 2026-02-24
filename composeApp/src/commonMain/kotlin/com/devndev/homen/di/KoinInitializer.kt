package com.devndev.homen.di

import com.devndev.homen.core.data.di.dataModule
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.KakaoUser
import com.devndev.homen.core.domain.di.domainModule
import org.koin.core.context.startKoin
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
        viewModelModule
    )
}

/**
 * iOS 전용 초기화 브릿지
 *
 */
fun doInitKoin(kakaoAuthenticator: SocialAuthenticator<KakaoUser>) = initKoin {
    modules(module {
        single { kakaoAuthenticator }
    })
}

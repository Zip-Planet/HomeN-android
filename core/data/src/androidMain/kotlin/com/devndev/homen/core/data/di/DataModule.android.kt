package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.auth.KakaoAuthenticatorImpl
import com.devndev.homen.core.domain.auth.SocialAuthenticator
import com.devndev.homen.core.domain.auth.model.KakaoUser
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * 안드로이드 전용 데이터 모듈 구현
 */
actual val dataModule: Module = module {
    // 1. 공통 모듈 포함
    includes(commonDataModule)

    single<SocialAuthenticator<KakaoUser>>(named("kakao")) { KakaoAuthenticatorImpl(get()) }
}

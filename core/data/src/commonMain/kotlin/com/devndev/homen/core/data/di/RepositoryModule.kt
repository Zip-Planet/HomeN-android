package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.repository.AuthRepositoryImpl
import com.devndev.homen.core.domain.repository.AuthRepository
import org.koin.dsl.module

/**
 * 공통 Repository 의존성
 */
val repositoryModule = module {
    // KakaoAuthenticator와 AppleAuthenticator 두 가지를 각각 주입받음
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}

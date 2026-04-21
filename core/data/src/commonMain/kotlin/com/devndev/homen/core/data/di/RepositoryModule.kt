package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.repository.AuthRepositoryImpl
import com.devndev.homen.core.data.repository.HomeRepositoryImpl
import com.devndev.homen.core.domain.repository.AuthRepository
import com.devndev.homen.core.domain.repository.HomeRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * 공통 Repository 의존성
 */
val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            kakaoAuthenticator = get(named("kakao")),
            appleAuthenticator = get(named("apple")),
            authService = get()
        )
    }
    single<HomeRepository> {
        HomeRepositoryImpl(
            homeService = get()
        )
    }
}

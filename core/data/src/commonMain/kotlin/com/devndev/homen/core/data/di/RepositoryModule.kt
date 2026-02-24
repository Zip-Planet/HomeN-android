package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.repository.AuthRepositoryImpl
import com.devndev.homen.core.domain.repository.AuthRepository
import org.koin.dsl.module

/**
 * 공통 Repository 의존성
 */
val repositoryModule = module {
    // SocialAuthenticator는 플랫폼별(androidMain/iosMain)에서 등록된 것을 주입받음
    single<AuthRepository> { AuthRepositoryImpl(get()) }
}

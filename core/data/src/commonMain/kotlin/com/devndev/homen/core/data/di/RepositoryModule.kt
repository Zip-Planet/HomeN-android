package com.devndev.homen.core.data.di

import org.koin.dsl.module

/**
 * Repository 구현체 관련 의존성
 */
val repositoryModule = module {
    // single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
}

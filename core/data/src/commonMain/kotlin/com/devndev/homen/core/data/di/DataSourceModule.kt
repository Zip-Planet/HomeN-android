package com.devndev.homen.core.data.di

import org.koin.dsl.module

/**
 * Remote/Local 데이터 소스 관련 의존성
 */
val dataSourceModule = module {
    // single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }
}

package com.devndev.homen.core.data.di

import org.koin.dsl.module

/**
 * 데이터 레이어 전체 모듈 통합
 */
val dataModule = module {
    includes(networkModule, dataSourceModule, repositoryModule)
}

package com.devndev.homen.core.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * 공통 데이터 모듈 (모든 플랫폼 공통)
 */
val commonDataModule = module {
    includes(networkModule, dataSourceModule, repositoryModule)
}

/**
 * 플랫폼별 최종 데이터 모듈
 */
expect val dataModule: Module

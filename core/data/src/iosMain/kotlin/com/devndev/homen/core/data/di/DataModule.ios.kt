package com.devndev.homen.core.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS 전용 데이터 모듈 구현
 */
actual val dataModule: Module = module {
    // 공통 모듈 포함
    includes(commonDataModule)
}

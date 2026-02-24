package com.devndev.homen.core.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * iOS 전용 데이터 모듈 구현
 */
actual val dataModule: Module = module {
    // 공통 모듈 포함
    includes(commonDataModule)
    
    // iOS용 Authenticator는 KoinInitializer의 doInitKoin에서 주입받아 등록할 예정입니다.
}

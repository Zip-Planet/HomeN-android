package com.devndev.homen.core.domain.di

import org.koin.dsl.module

/**
 * 도메인 레이어 전체 모듈 통합
 */
val domainModule = module {
    includes(useCaseModule)
}

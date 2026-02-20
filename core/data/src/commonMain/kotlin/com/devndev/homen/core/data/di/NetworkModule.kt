package com.devndev.homen.core.data.di

import org.koin.dsl.module

/**
 * API 통신(Ktor 등) 관련 의존성
 */
val networkModule = module {
    // single { HttpClient { ... } }
}

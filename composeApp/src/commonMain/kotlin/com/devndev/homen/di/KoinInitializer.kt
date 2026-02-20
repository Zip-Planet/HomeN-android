package com.devndev.homen.di

import com.devndev.homen.core.data.di.dataModule
import com.devndev.homen.core.domain.di.domainModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * 앱 전체의 Koin 의존성을 하나로 합쳐서 초기화합니다.
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        dataModule,
        domainModule,
        viewModelModule
    )
}

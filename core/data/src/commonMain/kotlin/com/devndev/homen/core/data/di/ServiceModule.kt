package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.service.auth.AuthService
import com.devndev.homen.core.data.service.auth.AuthServiceImpl
import org.koin.dsl.module


val serviceModule = module {
    single<AuthService> { AuthServiceImpl(get()) }
}
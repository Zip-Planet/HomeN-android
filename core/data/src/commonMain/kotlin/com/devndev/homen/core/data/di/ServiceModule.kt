package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.service.auth.AuthService
import com.devndev.homen.core.data.service.auth.AuthServiceImpl
import com.devndev.homen.core.data.service.home.HomeService
import com.devndev.homen.core.data.service.home.HomeServiceImpl
import com.devndev.homen.core.data.service.user.UserService
import com.devndev.homen.core.data.service.user.UserServiceImpl
import org.koin.dsl.module


val serviceModule = module {
    single<AuthService> { AuthServiceImpl(get()) }
    single<HomeService> { HomeServiceImpl(get(), get()) }
    single<UserService> { UserServiceImpl(get(), get()) }
}
package com.devndev.homen.di

import com.devndev.homen.ui.intro.login.viewmodel.LoginViewModel
import com.devndev.homen.ui.intro.register.viewmodel.RegisterViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * UI 레이어의 ViewModel 의존성
 */
val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}

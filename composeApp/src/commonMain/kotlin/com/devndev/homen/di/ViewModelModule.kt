package com.devndev.homen.di

import com.devndev.homen.ui.intro.login.viewmodel.LoginViewModel
import com.devndev.homen.ui.intro.register.viewmodel.RegisterViewModel
import com.devndev.homen.ui.main.homeentry.joindone.viewmodel.JoinDoneViewModel
import com.devndev.homen.ui.main.homeentry.joinconfirm.viewmodel.JoinConfirmViewModel
import com.devndev.homen.ui.main.homeentry.join.viewmodel.CodeEnterViewModel
import com.devndev.homen.ui.main.homeentry.main.viewmodel.HomeEntryViewModel
import com.devndev.homen.ui.main.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * UI 레이어의 ViewModel 의존성
 */
val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::HomeEntryViewModel)
    viewModelOf(::CodeEnterViewModel)
    viewModelOf(::JoinConfirmViewModel)
    viewModelOf(::JoinDoneViewModel)
}

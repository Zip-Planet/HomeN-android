package com.devndev.homen.core.domain.di

import com.devndev.homen.core.domain.usecase.auth.ClearTokenUseCase
import com.devndev.homen.core.domain.usecase.auth.CommitTokensUseCase
import com.devndev.homen.core.domain.usecase.auth.KakaoLoginToServerUseCase
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import com.devndev.homen.core.domain.usecase.auth.SaveTokensUseCase
import com.devndev.homen.core.domain.usecase.auth.SocialLoginUseCase
import com.devndev.homen.core.domain.usecase.home.CreateHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetHasHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.splash.CheckTokenUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.core.domain.usecase.user.UpdateProfileUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * 도메인 레이어의 비즈니스 로직(UseCase) 의존성
 */
val useCaseModule = module {
    // auth
    factoryOf(::SocialLoginUseCase)
    factoryOf(::KakaoLoginToServerUseCase)
    factoryOf(::ClearTokenUseCase)
    factoryOf(::SaveTokensUseCase)
    factoryOf(::CommitTokensUseCase)
    factoryOf(::LogoutUseCase)

    // home
    factoryOf(::CreateHomeUseCase)
    factoryOf(::GetHomeUseCase)
    factoryOf(::GetHasHomeUseCase)

    // user
    factoryOf(::UpdateProfileUseCase)
    factoryOf(::GetMyInfoUseCase)

    // splash
    factoryOf(::CheckTokenUseCase)
}

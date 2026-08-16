package com.devndev.homen.di

import com.devndev.homen.ui.intro.login.viewmodel.LoginViewModel
import com.devndev.homen.ui.intro.register.viewmodel.RegisterViewModel
import com.devndev.homen.ui.intro.splash.viewmodel.SplashViewModel
import com.devndev.homen.ui.main.assignment.main.viewmodel.AssignmentViewModel
import com.devndev.homen.ui.main.board.main.viewmodel.BoardViewModel
import com.devndev.homen.ui.main.home.choredetail.viewmodel.ChoreDetailViewModel
import com.devndev.homen.ui.main.home.choremanage.viewmodel.ChoreManageViewModel
import com.devndev.homen.ui.main.home.createchore.viewmodel.CreateChoreViewModel
import com.devndev.homen.ui.main.home.main.viewmodel.HomeViewModel
import com.devndev.homen.ui.main.home.memo.viewModel.MemoViewModel
import com.devndev.homen.ui.main.home.starterpack.viewmodel.StarterPackViewModel
import com.devndev.homen.ui.main.home.starterpackpreview.viewmodel.StarterPackPreviewViewModel
import com.devndev.homen.ui.main.homeintro.create.viewmodel.CreateHomeViewModel
import com.devndev.homen.ui.main.homeintro.join.viewmodel.CodeEnterViewModel
import com.devndev.homen.ui.main.homeintro.joinconfirm.viewmodel.JoinConfirmViewModel
import com.devndev.homen.ui.main.homeintro.joindone.viewmodel.JoinDoneViewModel
import com.devndev.homen.ui.main.homeintro.main.viewmodel.HomeIntroViewModel
import com.devndev.homen.ui.main.reward.detail.viewmodel.RewardDetailViewModel
import com.devndev.homen.ui.main.reward.edit.viewmodel.RewardEditViewModel
import com.devndev.homen.ui.main.reward.main.viewmodel.RewardViewModel
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
    viewModelOf(::HomeIntroViewModel)
    viewModelOf(::CodeEnterViewModel)
    viewModelOf(::JoinConfirmViewModel)
    viewModelOf(::JoinDoneViewModel)
    viewModelOf(::CreateHomeViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ChoreManageViewModel)
    viewModelOf(::CreateChoreViewModel)
    viewModelOf(::ChoreDetailViewModel)
    viewModelOf(::MemoViewModel)
    viewModelOf(::StarterPackViewModel)
    viewModelOf(::StarterPackPreviewViewModel)
    viewModelOf(::BoardViewModel)
    viewModelOf(::AssignmentViewModel)
    viewModelOf(::RewardViewModel)
    viewModelOf(::RewardEditViewModel)
    viewModelOf(::RewardDetailViewModel)
}

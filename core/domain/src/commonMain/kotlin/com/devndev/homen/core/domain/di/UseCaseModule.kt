package com.devndev.homen.core.domain.di

import com.devndev.homen.core.domain.usecase.auth.ClearTokenUseCase
import com.devndev.homen.core.domain.usecase.auth.CommitTokensUseCase
import com.devndev.homen.core.domain.usecase.auth.KakaoLoginToServerUseCase
import com.devndev.homen.core.domain.usecase.auth.LogoutUseCase
import com.devndev.homen.core.domain.usecase.auth.SaveTokensUseCase
import com.devndev.homen.core.domain.usecase.auth.SocialLoginUseCase
import com.devndev.homen.core.domain.usecase.board.AcceptExchangeUseCase
import com.devndev.homen.core.domain.usecase.board.AcceptHelpUseCase
import com.devndev.homen.core.domain.usecase.board.CreateExchangeUseCase
import com.devndev.homen.core.domain.usecase.board.CreateHelpUseCase
import com.devndev.homen.core.domain.usecase.board.DeleteExchangeUseCase
import com.devndev.homen.core.domain.usecase.board.DeleteHelpUseCase
import com.devndev.homen.core.domain.usecase.board.GetBoardUseCase
import com.devndev.homen.core.domain.usecase.board.RejectExchangeUseCase
import com.devndev.homen.core.domain.usecase.home.CancelCompleteChoreUseCase
import com.devndev.homen.core.domain.usecase.home.CompleteChoreUseCase
import com.devndev.homen.core.domain.usecase.home.ConfirmAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.CreateAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.CreateChoreUseCase
import com.devndev.homen.core.domain.usecase.home.CreateHomeUseCase
import com.devndev.homen.core.domain.usecase.home.CreateMemoUseCase
import com.devndev.homen.core.domain.usecase.home.DeleteChoreUseCase
import com.devndev.homen.core.domain.usecase.home.DeleteHomeUseCase
import com.devndev.homen.core.domain.usecase.home.DeleteMemoUseCase
import com.devndev.homen.core.domain.usecase.home.EditChoreUseCase
import com.devndev.homen.core.domain.usecase.home.EditMemoUseCase
import com.devndev.homen.core.domain.usecase.home.GetAssignmentsUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoreDetailUseCase
import com.devndev.homen.core.domain.usecase.home.GetChoresUseCase
import com.devndev.homen.core.domain.usecase.home.GetHasHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetJoinHomeUseCase
import com.devndev.homen.core.domain.usecase.home.GetMemosUseCase
import com.devndev.homen.core.domain.usecase.home.GetWeeklyReportUseCase
import com.devndev.homen.core.domain.usecase.home.JoinHomeUseCase
import com.devndev.homen.core.domain.usecase.home.LeaveHomeUseCase
import com.devndev.homen.core.domain.usecase.home.NudgeAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.RegenerateAssignmentUseCase
import com.devndev.homen.core.domain.usecase.home.TransferAdminUseCase
import com.devndev.homen.core.domain.usecase.notification.GetNotificationSettingUseCase
import com.devndev.homen.core.domain.usecase.notification.GetNotificationsUseCase
import com.devndev.homen.core.domain.usecase.notification.ReadNotificationUseCase
import com.devndev.homen.core.domain.usecase.notification.UpdateNotificationSettingUseCase
import com.devndev.homen.core.domain.usecase.reward.ClaimRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.CreateRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.DeleteRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.EditRewardUseCase
import com.devndev.homen.core.domain.usecase.reward.GetRewardDetailUseCase
import com.devndev.homen.core.domain.usecase.reward.GetRewardsUseCase
import com.devndev.homen.core.domain.usecase.splash.CheckTokenUseCase
import com.devndev.homen.core.domain.usecase.user.GetMyInfoUseCase
import com.devndev.homen.core.domain.usecase.user.UpdateProfileUseCase
import com.devndev.homen.core.domain.usecase.user.ValidateNicknameUseCase
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
    factoryOf(::DeleteHomeUseCase)
    factoryOf(::GetJoinHomeUseCase)
    factoryOf(::JoinHomeUseCase)
    factoryOf(::CreateChoreUseCase)
    factoryOf(::GetChoresUseCase)
    factoryOf(::DeleteChoreUseCase)
    factoryOf(::GetChoreDetailUseCase)
    factoryOf(::EditChoreUseCase)
    factoryOf(::GetMemosUseCase)
    factoryOf(::CreateMemoUseCase)
    factoryOf(::EditMemoUseCase)
    factoryOf(::DeleteMemoUseCase)
    factoryOf(::GetAssignmentsUseCase)
    factoryOf(::CreateAssignmentUseCase)
    factoryOf(::ConfirmAssignmentUseCase)
    factoryOf(::RegenerateAssignmentUseCase)
    factoryOf(::CompleteChoreUseCase)
    factoryOf(::CancelCompleteChoreUseCase)
    factoryOf(::GetWeeklyReportUseCase)
    factoryOf(::TransferAdminUseCase)
    factoryOf(::LeaveHomeUseCase)
    factoryOf(::NudgeAssignmentUseCase)

    // board
    factoryOf(::GetBoardUseCase)
    factoryOf(::CreateHelpUseCase)
    factoryOf(::DeleteHelpUseCase)
    factoryOf(::AcceptHelpUseCase)
    factoryOf(::CreateExchangeUseCase)
    factoryOf(::DeleteExchangeUseCase)
    factoryOf(::AcceptExchangeUseCase)
    factoryOf(::RejectExchangeUseCase)

    // reward
    factoryOf(::GetRewardsUseCase)
    factoryOf(::CreateRewardUseCase)
    factoryOf(::EditRewardUseCase)
    factoryOf(::DeleteRewardUseCase)
    factoryOf(::GetRewardDetailUseCase)
    factoryOf(::ClaimRewardUseCase)

    // user
    factoryOf(::UpdateProfileUseCase)
    factoryOf(::GetMyInfoUseCase)
    factoryOf(::ValidateNicknameUseCase)

    // splash
    factoryOf(::CheckTokenUseCase)

    // notification
    factoryOf(::GetNotificationSettingUseCase)
    factoryOf(::UpdateNotificationSettingUseCase)
    factoryOf(::GetNotificationsUseCase)
    factoryOf(::ReadNotificationUseCase)
}

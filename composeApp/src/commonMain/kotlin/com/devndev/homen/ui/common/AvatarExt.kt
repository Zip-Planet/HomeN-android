package com.devndev.homen.ui.common

import com.devndev.homen.core.domain.model.home.AvatarType
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.chef_avatar
import homen.composeapp.generated.resources.farmer_avatar
import homen.composeapp.generated.resources.guard_avatar
import homen.composeapp.generated.resources.hero_avatar
import homen.composeapp.generated.resources.wizard_avatar
import homen.composeapp.generated.resources.zombie_avatar
import org.jetbrains.compose.resources.DrawableResource

/**
 * 도메인 모델인 AvatarType을 UI 리소스인 DrawableResource로 매핑하는 확장 프로퍼티
 */
val AvatarType.resource: DrawableResource
    get() = when (this) {
        AvatarType.CHEF -> Res.drawable.chef_avatar
        AvatarType.WIZARD -> Res.drawable.wizard_avatar
        AvatarType.HERO -> Res.drawable.hero_avatar
        AvatarType.GUARD -> Res.drawable.guard_avatar
        AvatarType.ZOMBIE -> Res.drawable.zombie_avatar
        AvatarType.FARMER -> Res.drawable.farmer_avatar
    }

package com.devndev.homen.ui.common

import com.devndev.homen.core.domain.model.home.HomeIconType
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home1_big_icon
import homen.composeapp.generated.resources.home1_small_icon
import homen.composeapp.generated.resources.home2_big_icon
import homen.composeapp.generated.resources.home2_small_icon
import homen.composeapp.generated.resources.home3_big_icon
import homen.composeapp.generated.resources.home3_small_icon
import org.jetbrains.compose.resources.DrawableResource

val HomeIconType.bigResource: DrawableResource
    get() = when(this) {
        HomeIconType.HOME1 -> Res.drawable.home1_big_icon
        HomeIconType.HOME2 -> Res.drawable.home2_big_icon
        HomeIconType.HOME3 -> Res.drawable.home3_big_icon
    }


val HomeIconType.smallResource: DrawableResource
    get() = when(this) {
        HomeIconType.HOME1 -> Res.drawable.home1_small_icon
        HomeIconType.HOME2 -> Res.drawable.home2_small_icon
        HomeIconType.HOME3 -> Res.drawable.home3_small_icon
    }
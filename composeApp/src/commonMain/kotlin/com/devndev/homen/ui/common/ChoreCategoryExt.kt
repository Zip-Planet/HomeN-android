package com.devndev.homen.ui.common

import com.devndev.homen.core.domain.model.chore.ChoreCategory
import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.barhroom_icon
import homen.composeapp.generated.resources.cleaning_icon
import homen.composeapp.generated.resources.kitchen_icon
import homen.composeapp.generated.resources.laundry_icon
import homen.composeapp.generated.resources.trash_icon
import org.jetbrains.compose.resources.DrawableResource

val ChoreCategory.resource: DrawableResource
    get() = when(this) {
        ChoreCategory.TRASH -> Res.drawable.trash_icon
        ChoreCategory.BATHROOM -> Res.drawable.barhroom_icon
        ChoreCategory.CLEANING -> Res.drawable.cleaning_icon
        ChoreCategory.KITCHEN -> Res.drawable.kitchen_icon
        ChoreCategory.LAUNDRY -> Res.drawable.laundry_icon
    }
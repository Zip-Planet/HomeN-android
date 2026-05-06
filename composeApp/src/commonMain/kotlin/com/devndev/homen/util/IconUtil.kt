package com.devndev.homen.util

import homen.composeapp.generated.resources.Res
import homen.composeapp.generated.resources.home1_big_icon
import homen.composeapp.generated.resources.home1_small_icon
import homen.composeapp.generated.resources.home2_big_icon
import homen.composeapp.generated.resources.home2_small_icon
import homen.composeapp.generated.resources.home3_big_icon
import homen.composeapp.generated.resources.home3_small_icon
import org.jetbrains.compose.resources.DrawableResource

object IconUtil {

    fun getHomeSmallIcon(id: Int): DrawableResource {
        return when(id) {
            1 -> Res.drawable.home1_small_icon
            2 -> Res.drawable.home2_small_icon
            3 -> Res.drawable.home3_small_icon
            else -> Res.drawable.home1_small_icon
        }
    }

    fun getHomeBigIcon(id: Int): DrawableResource {
        return when(id) {
            1 -> Res.drawable.home1_big_icon
            2 -> Res.drawable.home2_big_icon
            3 -> Res.drawable.home3_big_icon
            else -> Res.drawable.home1_big_icon
        }
    }
}
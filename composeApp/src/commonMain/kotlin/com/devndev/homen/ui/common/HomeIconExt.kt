//package com.devndev.homen.ui.common
//
//import com.devndev.homen.core.domain.model.home.HomeIconType
//import homen.composeapp.generated.resources.Res
//import homen.composeapp.generated.resources.home1_big_icon
//import homen.composeapp.generated.resources.home1_small_icon
//import homen.composeapp.generated.resources.home2_big_icon
//import homen.composeapp.generated.resources.home2_small_icon
//import homen.composeapp.generated.resources.home3_big_icon
//import homen.composeapp.generated.resources.home3_small_icon
//import org.jetbrains.compose.resources.DrawableResource
//
///**
// * 서버에서 내려주는 Int(ID)를 바로 작은 아이콘 리소스로 매핑
// */
//val Int.homeSmallResource: DrawableResource
//    get() = HomeIconType.fromId(this).let { type ->
//        when (type) {
//            HomeIconType.HOME1 -> Res.drawable.home1_small_icon
//            HomeIconType.HOME2 -> Res.drawable.home2_small_icon
//            HomeIconType.HOME3 -> Res.drawable.home3_small_icon
//        }
//    }
//
///**
// * 서버에서 내려주는 Int(ID)를 바로 큰 아이콘 리소스로 매핑
// */
//val Int.homeBigResource: DrawableResource
//    get() = HomeIconType.fromId(this).let { type ->
//        when (type) {
//            HomeIconType.HOME1 -> Res.drawable.home1_big_icon
//            HomeIconType.HOME2 -> Res.drawable.home2_big_icon
//            HomeIconType.HOME3 -> Res.drawable.home3_big_icon
//        }
//    }

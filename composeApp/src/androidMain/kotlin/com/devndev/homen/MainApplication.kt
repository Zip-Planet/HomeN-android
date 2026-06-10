package com.devndev.homen

import android.app.Application
import com.devndev.homen.di.initKoin
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import com.devndev.homen.BuildConfig

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)

        initKoin {
            androidLogger()
            androidContext(this@MainApplication)
        }

        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
    }
}

package com.devndev.homen.di

import com.devndev.homen.util.IosShareManager
import com.devndev.homen.util.ShareManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val managerModule = module {
    single<ShareManager> {
        IosShareManager(
            onKakaoShare = get(named("onKakaoShare"))
        )
    }
}

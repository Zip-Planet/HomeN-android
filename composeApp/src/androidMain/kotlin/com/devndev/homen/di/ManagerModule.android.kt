package com.devndev.homen.di

import com.devndev.homen.util.AndroidShareManager
import com.devndev.homen.util.ShareManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val managerModule = module {
    single<ShareManager> { AndroidShareManager(androidContext()) }
}

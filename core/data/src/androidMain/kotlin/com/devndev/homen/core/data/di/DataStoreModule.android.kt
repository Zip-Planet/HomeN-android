package com.devndev.homen.core.data.di

import android.content.Context
import com.devndev.homen.core.data.local.DATASTORE_FILE_NAME
import com.devndev.homen.core.data.local.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataStoreModule: Module = module {
    single {
        val context: Context = get()
        createDataStore {
            context.filesDir.resolve(DATASTORE_FILE_NAME).absolutePath
        }
    }
}

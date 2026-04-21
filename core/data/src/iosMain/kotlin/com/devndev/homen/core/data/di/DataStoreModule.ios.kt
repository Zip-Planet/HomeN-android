package com.devndev.homen.core.data.di

import com.devndev.homen.core.data.local.DATASTORE_FILE_NAME
import com.devndev.homen.core.data.local.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val dataStoreModule: Module = module {
    single {
        createDataStore {
            val directory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = true,
                error = null
            )
            requireNotNull(directory).path + "/" + DATASTORE_FILE_NAME
        }
    }
}

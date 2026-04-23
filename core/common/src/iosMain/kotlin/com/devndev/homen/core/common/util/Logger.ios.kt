package com.devndev.homen.core.common.util

import platform.Foundation.NSLog

actual object Logger {
    actual fun d(tag: String?, message: String) {
        NSLog("DEBUG: [%s] %s", tag, message)
    }

    actual fun e(tag: String?, message: String, throwable: Throwable?) {
        val errorMsg = throwable?.let { "\n${it.message}" } ?: ""
        NSLog("ERROR: [%s] %s%s", tag, message, errorMsg)
    }

    actual fun i(tag: String?, message: String) {
        NSLog("INFO: [%s] %s", tag, message)
    }
}

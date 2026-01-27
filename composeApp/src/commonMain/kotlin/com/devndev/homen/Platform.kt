package com.devndev.homen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.bunjne.bbit

enum class Platform {
    ANDROID, IOS
}

expect fun getPlatform(): Platform
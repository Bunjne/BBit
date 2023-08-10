package com.bunjne.bbit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
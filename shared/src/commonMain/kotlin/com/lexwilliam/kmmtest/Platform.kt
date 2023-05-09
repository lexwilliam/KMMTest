package com.lexwilliam.kmmtest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
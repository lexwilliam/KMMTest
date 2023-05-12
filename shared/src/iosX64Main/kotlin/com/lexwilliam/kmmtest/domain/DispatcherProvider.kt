package com.lexwilliam.kmmtest.domain

import kotlin.coroutines.CoroutineContext

actual class DispatcherProvider actual constructor() {
    actual fun main(): CoroutineContext {
        TODO("Not yet implemented")
    }

    actual fun io(): CoroutineContext {
        TODO("Not yet implemented")
    }

    actual fun default(): CoroutineContext {
        TODO("Not yet implemented")
    }

    actual fun unconfined(): CoroutineContext {
        TODO("Not yet implemented")
    }
}
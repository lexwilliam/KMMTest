package com.lexwilliam.kmmtest.domain.model

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual class DispatcherProvider actual constructor() {
    actual fun main(): CoroutineContext = Dispatchers.Main

    actual fun io(): CoroutineContext = Dispatchers.IO

    actual fun default(): CoroutineContext = Dispatchers.Default

    actual fun unconfined(): CoroutineContext = Dispatchers.Unconfined
}
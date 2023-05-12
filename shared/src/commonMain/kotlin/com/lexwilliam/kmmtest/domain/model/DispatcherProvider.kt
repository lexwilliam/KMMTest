package com.lexwilliam.kmmtest.domain.model

import kotlin.coroutines.CoroutineContext

expect class DispatcherProvider() {
    fun main(): CoroutineContext
    fun io(): CoroutineContext
    fun default(): CoroutineContext
    fun unconfined(): CoroutineContext
}
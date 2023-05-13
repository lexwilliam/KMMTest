package com.lexwilliam.kmmtest.di

import com.lexwilliam.kmmtest.data.cache.DatabaseDriverFactory
import org.koin.dsl.module

val androidCacheModule = module {
    single { DatabaseDriverFactory(get()) }
}
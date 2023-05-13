package com.lexwilliam.kmmtest.di

import com.lexwilliam.kmmtest.data.TransactionLocalSource
import com.lexwilliam.kmmtest.data.cache.DatabaseDriverFactory
import com.lexwilliam.kmmtest.data.cache.TransactionLocalSourceImpl
import org.koin.dsl.module

val cacheModule = module {
    single<TransactionLocalSource> { TransactionLocalSourceImpl(get(), get())}
}
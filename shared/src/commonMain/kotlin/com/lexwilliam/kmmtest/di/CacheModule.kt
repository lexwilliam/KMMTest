package com.lexwilliam.kmmtest.di

import com.lexwilliam.kmmtest.data.TransactionLocalSource
import com.lexwilliam.kmmtest.data.cache.TransactionLocalSourceImpl
import org.koin.dsl.module

val databaseModule = module {
    single<TransactionLocalSource> { TransactionLocalSourceImpl(get(), get())}
}
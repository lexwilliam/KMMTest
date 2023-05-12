package com.lexwilliam.kmmtest.di

import com.lexwilliam.kmmtest.data.repository.TransactionRepositoryImpl
import com.lexwilliam.kmmtest.domain.TransactionRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
}
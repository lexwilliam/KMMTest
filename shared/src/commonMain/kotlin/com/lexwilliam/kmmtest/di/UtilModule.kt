package com.lexwilliam.kmmtest.di

import com.lexwilliam.kmmtest.domain.model.DispatcherProvider
import org.koin.dsl.module

val utilModule = module {
    single { DispatcherProvider() }
}
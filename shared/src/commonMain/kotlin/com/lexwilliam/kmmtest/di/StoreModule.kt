package com.lexwilliam.kmmtest.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.lexwilliam.kmmtest.presenter.home.HomeStoreFactory
import org.koin.dsl.module

val storeModule = module {
    factory<StoreFactory> { DefaultStoreFactory() }
    factory { HomeStoreFactory(storeFactory = get(), repository = get()).create() }
}
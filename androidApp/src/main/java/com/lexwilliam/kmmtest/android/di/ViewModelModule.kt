package com.lexwilliam.kmmtest.android.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.lexwilliam.kmmtest.android.add.AddViewModel
import com.lexwilliam.kmmtest.android.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { AddViewModel(get()) }
}
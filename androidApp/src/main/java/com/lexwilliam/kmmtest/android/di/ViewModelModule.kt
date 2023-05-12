package com.lexwilliam.kmmtest.android.di

import com.lexwilliam.kmmtest.android.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
package com.lexwilliam.kmmtest.android

import android.app.Application
import com.lexwilliam.kmmtest.android.di.viewModelModule
import com.lexwilliam.kmmtest.di.cacheModule
import com.lexwilliam.kmmtest.di.repositoryModule
import com.lexwilliam.kmmtest.di.storeModule
import com.lexwilliam.kmmtest.di.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                repositoryModule,
                cacheModule,
                viewModelModule,
                storeModule,
                utilModule
            )
        }
    }
}
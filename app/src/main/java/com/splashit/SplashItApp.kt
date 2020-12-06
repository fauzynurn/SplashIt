package com.splashit

import android.app.Application
import com.splashit.core.di.databaseModule
import com.splashit.core.di.networkModule
import com.splashit.core.di.repositoryModule
import com.splashit.di.useCaseModule
import com.splashit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SplashItApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@SplashItApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}
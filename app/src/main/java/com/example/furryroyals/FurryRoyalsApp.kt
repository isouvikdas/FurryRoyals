package com.example.furryroyals

import android.app.Application
import com.example.furryroyals.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FurryRoyalsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FurryRoyalsApp)
            androidLogger()
            modules(appModule)
        }
    }
}
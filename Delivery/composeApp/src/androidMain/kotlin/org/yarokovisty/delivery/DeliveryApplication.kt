package org.yarokovisty.delivery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.yarokovisty.delivery.di.initKoin

class DeliveryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DeliveryApplication)
            androidLogger()
        }
    }
}

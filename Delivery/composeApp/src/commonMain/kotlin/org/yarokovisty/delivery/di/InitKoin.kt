package org.yarokovisty.delivery.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.yarokovisty.delivery.di.module.appModule
import org.yarokovisty.delivery.di.module.commonModule
import org.yarokovisty.delivery.di.module.coreModule
import org.yarokovisty.delivery.di.module.encryptionModule
import org.yarokovisty.delivery.di.module.featureModule
import org.yarokovisty.delivery.di.module.navigationModule

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)

        modules(
            appModule,
            commonModule,
            coreModule,
            encryptionModule,
            featureModule,
            navigationModule,
        )
    }
}

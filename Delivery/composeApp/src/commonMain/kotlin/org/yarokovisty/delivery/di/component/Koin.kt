package org.yarokovisty.delivery.di.component

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.koinConfiguration
import org.yarokovisty.delivery.di.module.coreModule
import org.yarokovisty.delivery.di.module.featureModule

@Composable
fun AppComponent(content: @Composable () -> Unit) {
    KoinApplication(configuration = getKoinConfiguration(), content = content)
}

private fun getKoinConfiguration(): KoinConfiguration =
    koinConfiguration {
        modules(
            coreModule,
            featureModule,
        )
    }

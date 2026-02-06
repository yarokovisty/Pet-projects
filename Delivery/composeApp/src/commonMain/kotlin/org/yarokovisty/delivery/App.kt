package org.yarokovisty.delivery

import androidx.compose.runtime.Composable
import org.koin.compose.KoinApplication
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.di.getKoinConfiguration

@Composable
fun App() {
    KoinApplication(getKoinConfiguration()) {
        DeliveryTheme {
        }
    }
}

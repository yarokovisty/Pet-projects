package org.yarokovisty.delivery.ui

import androidx.compose.runtime.Composable
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.di.component.AppComponent
import org.yarokovisty.delivery.ui.screen.AppScreen

@Composable
fun App() {
    AppComponent {
        DeliveryTheme {
            AppScreen()
        }
    }
}

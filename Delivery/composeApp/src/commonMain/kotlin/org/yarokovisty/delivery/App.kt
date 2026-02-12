package org.yarokovisty.delivery

import androidx.compose.runtime.Composable
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.di.component.AppComponent
import org.yarokovisty.delivery.feature.delivery.main.impl.ui.screen.DeliveryMainScreen

@Composable
fun App() {
    AppComponent {
        DeliveryTheme {
            DeliveryMainScreen()
        }
    }
}

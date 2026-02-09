package org.yarokovisty.delivery

import androidx.compose.runtime.Composable
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.di.component.AppComponent

@Composable
fun App() {
    AppComponent {
        DeliveryTheme {
        }
    }
}

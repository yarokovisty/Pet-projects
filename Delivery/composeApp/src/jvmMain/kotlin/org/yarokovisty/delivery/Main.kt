package org.yarokovisty.delivery

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.yarokovisty.delivery.di.initKoin
import org.yarokovisty.delivery.ui.App

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Delivery"
    ) {
        App()
    }
}

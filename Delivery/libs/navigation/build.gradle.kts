plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            api(libs.compose.navigation.ui)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.libs.navigation"
}
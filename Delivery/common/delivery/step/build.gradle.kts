plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "org.yarokovisty.common.delivery.step"
}

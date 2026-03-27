plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.bundles.datastore)

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.core.storage"
}
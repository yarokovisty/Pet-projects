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
            implementation(project(":libs:encryption"))
            implementation(libs.bundles.datastore)

            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.core.storage"
}
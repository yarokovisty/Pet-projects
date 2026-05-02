plugins {
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }

        commonMain.dependencies {
            implementation(projects.common.auth)
            implementation(projects.core.network)
            implementation(projects.core.storage)

            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.common.profile.main"
}

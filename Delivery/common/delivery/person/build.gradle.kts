plugins {
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.storage)

            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
        }

        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.common.delivery.person"
}

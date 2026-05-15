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
            implementation(projects.common.delivery.calculator)
            implementation(projects.common.delivery.direction)
            implementation(projects.common.delivery.parcel)
            implementation(projects.common.delivery.payer)
            implementation(projects.common.delivery.person)
            implementation(projects.common.delivery.point)
            implementation(projects.core.network)
            implementation(projects.util.kotlin)

            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.common.delivery.order"
}

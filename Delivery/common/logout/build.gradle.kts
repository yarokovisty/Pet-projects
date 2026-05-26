plugins {
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
            implementation(projects.common.profile.main)

            implementation(libs.koin.core)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.common.logout"
}

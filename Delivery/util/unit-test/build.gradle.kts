plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.junit)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.util.unitTest"
}

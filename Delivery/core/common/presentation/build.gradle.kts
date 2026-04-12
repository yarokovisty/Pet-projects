plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.libs.coordinator)
            implementation(projects.util.coroutines)

            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.core.common.presentation"
}

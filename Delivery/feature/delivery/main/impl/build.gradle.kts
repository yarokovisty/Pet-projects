import org.gradle.kotlin.dsl.sourceSets

plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.common.presentation)
            implementation(projects.core.network)
            implementation(projects.design.theme)
            implementation(projects.design.uikit)
            implementation(projects.feature.delivery.main.api)
            implementation(projects.util.coroutines)
            implementation(projects.util.modifier)

            implementation(libs.compose.components.resources)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlin.serialization)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery.feature.delivery.main.impl"
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

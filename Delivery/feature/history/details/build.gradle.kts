plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    sourceSets {
        androidUnitTest.dependencies {
            implementation(projects.util.unitTest)

            implementation(libs.junit)
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }
        commonMain.dependencies {
            implementation(projects.common.delivery.calculator)
            implementation(projects.common.delivery.direction)
            implementation(projects.common.delivery.order)
            implementation(projects.common.delivery.point)
            implementation(projects.core.common.error)
            implementation(projects.core.common.presentation)
            implementation(projects.core.network)
            implementation(projects.design.resources)
            implementation(projects.design.uikit)
            implementation(projects.design.theme)
            implementation(projects.libs.coordinator)
            implementation(projects.libs.navigation)
            implementation(projects.util.coroutines)

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
    namespace = "org.yarokovisty.delivery.feature.history.details"
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}
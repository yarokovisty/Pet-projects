import org.gradle.kotlin.dsl.sourceSets

plugins {
    alias(libs.plugins.delivery.androidLibrary)
    alias(libs.plugins.delivery.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
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
            implementation(projects.common.delivery.person)
            implementation(projects.common.delivery.step)
            implementation(projects.common.profile.main)
            implementation(projects.common.validation)
            implementation(projects.core.common.presentation)
            implementation(projects.design.resources)
            implementation(projects.design.theme)
            implementation(projects.design.uikit)
            implementation(projects.libs.navigation)
            implementation(projects.util.phone)
            implementation(projects.util.validation)

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
    namespace = "org.yarokovisty.delivery.feature.delivery.person"
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

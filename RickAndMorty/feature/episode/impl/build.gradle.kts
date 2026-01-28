import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.ksp)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
        }

        commonMain.dependencies {
            implementation(projects.design.component)
            implementation(projects.design.resources)
            implementation(projects.common.data)
            implementation(projects.common.presentation)
            implementation(projects.core.navigation)
            implementation(projects.core.network)
            implementation(projects.feature.character.api)
            implementation(projects.feature.episode.api)
            implementation(projects.library.navigationKsp.annotation)
            implementation(projects.library.result)
            implementation(projects.util)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            implementation(libs.compose.viewmodel)
            implementation(libs.compose.lifecycle.runtime)
            implementation(libs.compose.navigation)

            implementation(libs.kotlin.serialization.json)

            implementation(libs.bundles.ktor)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
    }
}

android {
    namespace = "org.pet.project.rickandmorty.feature.episode.impl"
    compileSdk = libs.versions.android.compile.sdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }
}

dependencies {
    add("kspCommonMainMetadata", projects.library.navigationKsp.processor)
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
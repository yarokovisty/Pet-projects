import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    sourceSets {
        androidUnitTest.dependencies {
            implementation(projects.util.unitTest)

            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk)
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(projects.common.auth)
            implementation(projects.common.delivery.calculator)
            implementation(projects.common.delivery.direction)
            implementation(projects.common.delivery.order)
            implementation(projects.common.delivery.parcel)
            implementation(projects.common.delivery.payer)
            implementation(projects.common.delivery.person)
            implementation(projects.common.delivery.point)
            implementation(projects.common.delivery.step)
            implementation(projects.common.logout)
            implementation(projects.common.profile.main)
            implementation(projects.common.validation)
            implementation(projects.core.common.coroutines)
            implementation(projects.core.common.presentation)
            implementation(projects.core.network)
            implementation(projects.core.storage)
            implementation(projects.design.resources)
            implementation(projects.design.theme)
            implementation(projects.design.uikit)
            implementation(projects.feature.delivery.calculator)
            implementation(projects.feature.delivery.direction)
            implementation(projects.feature.delivery.main)
            implementation(projects.feature.delivery.order)
            implementation(projects.feature.delivery.payer)
            implementation(projects.feature.delivery.person)
            implementation(projects.feature.delivery.point)
            implementation(projects.feature.history.main)
            implementation(projects.feature.login)
            implementation(projects.feature.profile.main)
            implementation(projects.libs.encryption)
            implementation(projects.libs.navigation)

            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.navigation.lifecycle)
            implementation(libs.compose.runtime)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.kotlin.serialization)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.yarokovisty.delivery"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.yarokovisty.delivery"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "org.yarokovisty.delivery.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.yarokovisty.delivery"
            packageVersion = "1.0.0"
        }
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}


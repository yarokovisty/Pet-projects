buildscript {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    configurations.classpath {
        resolutionStrategy {
            force(libs.findLibrary("jetbrains-annotations").get())
        }
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.koin.compiler) apply false
    alias(libs.plugins.kotlin.compose) apply false

    alias(libs.plugins.convention.android) apply false
    alias(libs.plugins.convention.detekt) apply false
}
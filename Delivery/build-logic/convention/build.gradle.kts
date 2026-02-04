plugins {
    `kotlin-dsl`
}

group = "org.yarokovisty.delivery.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("kmpPlugin") {
            id = "org.yarokovisty.delivery.kmp"
            implementationClass = "org.yarokovisty.delivery.buildlogic.convention.KMPConventionPlugin"
        }

        create("androidLibraryPlugin") {
            id = "org.yarokovisty.delivery.androidLibrary"
            implementationClass = "org.yarokovisty.delivery.buildlogic.convention.AndroidLibraryConventionPlugin"
        }
    }
}
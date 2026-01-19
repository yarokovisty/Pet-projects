plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.library.navigationKsp.annotation)
                implementation(libs.ksp.symbol.processing)
                implementation("com.squareup:kotlinpoet:1.16.0")
                implementation("com.squareup:kotlinpoet-ksp:1.16.0")
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

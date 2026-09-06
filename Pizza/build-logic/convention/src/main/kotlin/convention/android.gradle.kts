package convention

import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.LibraryPlugin

apply<LibraryPlugin>()
apply<DetektPlugin>()

configure<LibraryExtension> {
    namespace = "org.yarokovisty.shift_pizza." + project.name

    compileSdk = COMPILE_SDK

    defaultConfig {
        minSdk = MIN_SDK
    }

    compileOptions {
        val version = JavaVersion.toVersion(JAVA_VERSION)
        sourceCompatibility = version
        targetCompatibility = version
    }
}
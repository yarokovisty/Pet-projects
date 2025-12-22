plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.android.lint)
}

kotlin {
    androidLibrary {
        namespace = "org.pet.project.rickandmorty.shared"
        compileSdk = libs.versions.android.compile.sdk.get().toInt()
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName= "sharedKit"

            export(projects.common.data)
            export(projects.common.presentation)

            export(projects.core.dispatchers)
            export(projects.core.navigation)
            export(projects.core.network)

            export(projects.design.component)
            export(projects.design.theme)
            export(projects.design.resources)

            export(projects.feature.character.api)
            export(projects.feature.character.impl)
            export(projects.feature.episode)
            export(projects.feature.location)

            export(projects.library.logger)
            export(projects.library.result)

            export(projects.util)

            isStatic = true
        }
    }

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.common.data)
                api(projects.common.presentation)

                api(projects.core.dispatchers)
                api(projects.core.navigation)
                api(projects.core.network)

                api(projects.design.component)
                api(projects.design.theme)
                api(projects.design.resources)

                api(projects.feature.character.api)
                api(projects.feature.character.impl)
                api(projects.feature.episode)
                api(projects.feature.location)

                api(projects.library.logger)
                api(projects.library.result)

                api(projects.util)

                implementation(libs.kotlin.stdlib)
            }
        }
    }
}
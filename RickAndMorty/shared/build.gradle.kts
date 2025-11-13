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

            export(project(":library:result"))

            isStatic = true
        }
    }

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.library.result)

                implementation(libs.kotlin.stdlib)
            }
        }

        androidMain {
            dependencies {
            }
        }

        iosMain {
            dependencies {

            }
        }
    }
}
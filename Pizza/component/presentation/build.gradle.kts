plugins {
    alias(libs.plugins.convention.android)
    alias(libs.plugins.convention.detekt)
}

dependencies {
    implementation(projects.util.coroutines)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.kotlin.coroutines.core)
}
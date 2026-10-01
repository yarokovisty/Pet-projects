plugins {
    alias(libs.plugins.convention.android)
    alias(libs.plugins.convention.detekt)
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
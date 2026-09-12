plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.koin.compiler)

    alias(libs.plugins.convention.android)
}

dependencies {
    implementation(libs.koin.annotations)
    implementation(libs.koin.core)
    implementation(libs.kotlin.serialization)
    implementation(libs.ktor.cio)
    implementation(libs.ktor.contentNegotiation)
    implementation(libs.ktor.core)
    implementation(libs.ktor.serialization)
}
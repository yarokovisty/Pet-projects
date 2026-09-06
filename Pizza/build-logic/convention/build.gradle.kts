plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(plugin(libs.plugins.android.application))
    implementation(plugin(libs.plugins.detekt))
}

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>): Provider<String> =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
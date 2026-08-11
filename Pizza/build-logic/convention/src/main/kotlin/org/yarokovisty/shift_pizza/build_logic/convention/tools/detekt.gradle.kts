import dev.detekt.gradle.Detekt
import dev.detekt.gradle.plugin.DetektPlugin

apply<DetektPlugin>()

private val detektConfig = "$rootDir/config/detekt/detekt-config.yml"

tasks.withType<Detekt> {
    config.setFrom(detektConfig)
    
    parallel = true
}
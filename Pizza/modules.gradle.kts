val fileTree = fileTree(rootDir) {
    include("**/build.gradle.kts")
    exclude("**/build/**")
    exclude("**/gradle/**")
    exclude("build.gradle.kts")
    exclude("build-logic")
}

private fun includeModules(directory: File) {
    val rootPath = directory.toPath()

    fileTree.forEach { file ->
        val projectPath = file.parentFile.toPath()
        val relativePath = rootPath.relativize(projectPath)
        val rootProject = relativePath.toString().isEmpty()

        if (!rootProject) {
            val module = ":$relativePath".replace(File.separator, ":")
            include(module)
        }
    }
}

includeModules(rootDir)
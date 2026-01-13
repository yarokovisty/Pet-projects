package org.pet.project.rickandmorty.navigation.ksp.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

class LocalNavigatorProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedSymbols = resolver
            .getSymbolsWithAnnotation(ScreenNavigator::class.qualifiedName!!)
            .groupBy { it.validate() }
        val validSymbols = annotatedSymbols[true].orEmpty()
        val symbolsForReprocessing = annotatedSymbols[false].orEmpty()

        validSymbols
            .filterIsInstance<KSClassDeclaration>()
            .forEach { classDeclaration ->
                generateCompositionLocal(classDeclaration)
            }

        return symbolsForReprocessing
    }

    private fun generateCompositionLocal(classDeclaration: KSClassDeclaration) {
        val packageName = classDeclaration.packageName.asString()
        val className = classDeclaration.simpleName.asString()
        val localName = "Local$className"
        val fileName = "${className}CompositionLocal"

        val containingFile = classDeclaration.containingFile ?: return

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, containingFile),
            packageName = packageName,
            fileName = fileName
        )

        file.writer().use { writer ->
            writer.appendLine("package $packageName")
            writer.appendLine()
            writer.appendLine("import androidx.compose.runtime.staticCompositionLocalOf")
            writer.appendLine()
            writer.appendLine(
                "val $localName = staticCompositionLocalOf<$className> {"
            )
            writer.appendLine(
                "    error(\"$className not provided\")"
            )
            writer.appendLine("}")
        }
    }
}
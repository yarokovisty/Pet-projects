package org.pet.project.rickandmorty.navigation.ksp.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo
import org.pet.project.rickandmorty.navigation.ksp.annotation.ScreenNavigator

class NavigatorProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(ScreenNavigator::class.qualifiedName!!)
            .filterIsInstance<KSClassDeclaration>()
            .groupBy { it.validate() }

        val validSymbols = symbols[true].orEmpty()
        val symbolsForReprocessing = symbols[false].orEmpty()

        validSymbols.forEach(::generateNavigator)

        return symbolsForReprocessing
    }

    private fun generateNavigator(classDecl: KSClassDeclaration) {
        val className = classDecl.simpleName.asString()
        val packageName = classDecl.packageName.asString()

        val constructor = classDecl.primaryConstructor
            ?: error("Navigator $className must have primary constructor")
        val dependencies = extractNavigatorDependencies(constructor.parameters)

        val functionSpec = generateRememberFunction(
            functionName = className,
            navigatorClass = classDecl.toClassName(),
            dependencies = dependencies
        )

        FileSpec.builder(packageName, className)
            .addFunction(functionSpec)
            .build()
            .writeTo(codeGenerator, false)
    }

    private fun extractNavigatorDependencies(params: List<KSValueParameter>): List<NavigatorDependency> =
        params.map(::extractNavigatorDependency)

    private fun extractNavigatorDependency(param: KSValueParameter): NavigatorDependency {
        val name = param.name?.asString() ?: error("Unnamed constructor parameter")
        val type = param.type.resolve().declaration.qualifiedName?.asString() ?: error("Unnamed type")

        return when(type) {
            "org.pet.project.rickandmorty.core.navigation.navigator.NestedNavigator" -> NavigatorDependency(name, NavigatorRole.NESTED)
            "org.pet.project.rickandmorty.core.navigation.navigator.GlobalNavigator" -> NavigatorDependency(name, NavigatorRole.GLOBAL)
            else -> error("Navigator parameter is not valid")
        }
    }

    private fun generateRememberFunction(
        functionName: String,
        navigatorClass: ClassName,
        dependencies: List<NavigatorDependency>
    ): FunSpec {
        return FunSpec.builder(functionName)
            .internal()
            .composableFunction()
            .returns(navigatorClass)
            .apply {
                dependencies.forEach { dep ->
                    when (dep.role) {
                        NavigatorRole.NESTED ->
                            addStatement(
                                "val %L = %M.current",
                                dep.name,
                                localNestedNavigator()
                            )

                        NavigatorRole.GLOBAL ->
                            addStatement(
                                "val %L = %M.current",
                                dep.name,
                                localGlobalNavigator()
                            )
                    }
                }

                addStatement("")
                addStatement(
                    "return %M { %T(%L) }",
                    rememberMember(),
                    navigatorClass,
                    dependencies.joinToString { "${it.name} = ${it.name}" }
                )
            }
            .build()
    }

    private fun localNestedNavigator(): MemberName =
        MemberName(
            "org.pet.project.rickandmorty.core.navigation.navigator",
            "LocalNestedNavigator"
        )

    private fun localGlobalNavigator(): MemberName =
        MemberName(
            "org.pet.project.rickandmorty.core.navigation.navigator",
            "LocalGlobalNavigator"
        )

    private fun rememberMember(): MemberName =
        MemberName(
            "androidx.compose.runtime",
            "remember"
        )
}

private fun FunSpec.Builder.internal(): FunSpec.Builder = addModifiers(KModifier.INTERNAL)

private fun FunSpec.Builder.composableFunction(): FunSpec.Builder =
    addAnnotation(ClassName("androidx.compose.runtime", "Composable"))

private enum class NavigatorRole {
    GLOBAL,
    NESTED
}

private data class NavigatorDependency(
    val name: String,
    val role: NavigatorRole
)

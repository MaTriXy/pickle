package com.fourlastor.pickle

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Messager
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedAnnotationTypes("cucumber.api.java.*", "com.fourlastor.pickle.Pickle")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class PickleProcessor : AbstractProcessor() {

    override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
        if (annotations.isEmpty()) {
            return false
        }

        try {
            options(roundEnv).run {
                val parser = FeatureParser()
                val classConverter = createClassConverter(roundEnv, processingEnv.messager)

                val generator = ClassGenerator()
                val writer = ClassWriter(processingEnv, packageName)

                parser.parse(featuresDirPath)
                        .map { classConverter.convert(it) }
                        .filter { it.methods.isNotEmpty() }
                        .map { generator.generate(it) }
                        .forEach { writer.write(it) }
            }
        } catch (exception: PickleException) {
            processingEnv.messager.error("Pickle Error:\n${exception.message}\n")
        } finally {
            return false
        }
    }

    private fun Options.createClassConverter(roundEnv: RoundEnvironment, messager: Messager): ClassConverter {
        return ClassConverter(
                MethodsConverter(
                        MethodConverter(StatementConverter(roundEnv)),
                        strictMode,
                        messager
                ),
                HooksCreator(StatementHooksCreator(roundEnv))
        )
    }

    private fun Messager.error(message: String) {
        printMessage(Diagnostic.Kind.ERROR, message)
    }
}

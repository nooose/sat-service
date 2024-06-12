package com.sat.common.documentation.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.snippet.AbstractDescriptor
import org.springframework.restdocs.snippet.Attributes
import org.springframework.restdocs.snippet.IgnorableDescriptor
import kotlin.reflect.KClass

private const val CONSTRAINT_ATTR_KEY = "constraints"

abstract class AbstractSnippetDsl {
    protected fun <T : AbstractDescriptor<T>> addOptions(
        descriptor: T,
        optional: Boolean = false,
        constraint: String = "",
        ignored: Boolean = false,
    ) {
        if (ignored) {
            (descriptor as? IgnorableDescriptor<*>)?.ignored()
        }

        if (optional) {
            when (descriptor) {
                is RequestPartDescriptor -> descriptor.optional()
                is ParameterDescriptor -> descriptor.optional()
                is FieldDescriptor -> descriptor.optional()
            }
        }

        if (constraint.isNotBlank()) {
            descriptor.attributes(Attributes.key(CONSTRAINT_ATTR_KEY).value(constraint))
        }
    }

    protected fun <T : Enum<T>> getConstraintsText(enumClass: KClass<T>): String {
        return getConstraints(enumClass).joinToString(separator = "\n\n")
    }

    protected fun <T : Enum<T>> getConstraints(enumClass: KClass<T>): List<String> {
        return enumClass.java.enumConstants.map { getDescription(it) }
    }

    private fun <T : Enum<T>> getDescription(enum: T): String {
        if (enum.isDeprecated()) {
            return "${enum.name}(Deprecated)"
        }
        return enum.name
    }

    private fun Enum<*>.isDeprecated(): Boolean {
        val enumName = this.name
        return this.javaClass.declaredFields
            .filter { it.isEnumConstant }
            .filter { it.name == enumName }
            .any { it.isAnnotationPresent(Deprecated::class.java) }
    }
}

package com.sat.common.documentation.dsl

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.snippet.Snippet
import kotlin.reflect.KClass

@RestDocsMarker
class PartSnippetDsl : AbstractSnippetDsl() {
    private val descriptors: MutableList<RequestPartDescriptor> = mutableListOf()

    fun <T : Enum<T>> part(fieldName: String, description: String = "", optional: Boolean = false, constraint: KClass<T>, ignored: Boolean = false) {
        part(fieldName, description, optional, getConstraintsText(constraint), ignored)
    }

    fun part(fieldName: String, description: String = "", optional: Boolean = false, constraint: String = "", ignored: Boolean = false) {
        val descriptor = RequestDocumentation.partWithName(fieldName).description(description)
        addOptions(descriptor, optional, constraint, ignored)
        descriptors.add(descriptor)
    }

    fun snippet(): Snippet {
        return RequestDocumentation.requestParts(descriptors)
    }
}

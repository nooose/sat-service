package com.sat.common.documentation.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet
import kotlin.reflect.KClass

@RestDocsMarker
class HeaderSnippetDsl : AbstractSnippetDsl() {
    private val descriptors: MutableList<HeaderDescriptor> = mutableListOf()

    fun <T : Enum<T>> header(fieldName: String, description: String = "", optional: Boolean = false, constraint: KClass<T>, ignored: Boolean = false) {
        header(fieldName, description, optional, getConstraintsText(constraint), ignored)
    }

    fun header(fieldName: String, description: String = "", optional: Boolean = false, constraint: String = "", ignored: Boolean = false) {
        val descriptor = HeaderDocumentation.headerWithName(fieldName).description(description)
        addOptions(descriptor, optional, constraint, ignored)
        descriptors.add(descriptor)
    }

    fun requestHeaderSnippet(): Snippet {
        return HeaderDocumentation.requestHeaders(descriptors)
    }

    fun responseHeaderSnippet(): Snippet {
        return HeaderDocumentation.responseHeaders(descriptors)
    }
}

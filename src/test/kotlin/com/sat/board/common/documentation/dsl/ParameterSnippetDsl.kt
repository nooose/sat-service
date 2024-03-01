package com.sat.board.common.documentation.dsl

import com.megazone.act.cms.common.documentation.dsl.AbstractSnippetDsl
import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet
import kotlin.reflect.KClass

@RestDocsMarker
class ParameterSnippetDsl : AbstractSnippetDsl() {
    private val descriptors: MutableList<ParameterDescriptor> = mutableListOf()
    var pageable: Boolean = false

    fun <T : Enum<T>> param(fieldName: String, description: String = "", optional: Boolean = false, constraint: KClass<T>, ignored: Boolean = false) {
        param(fieldName, description, optional, getConstraintsText(constraint), ignored)
    }

    fun param(fieldName: String, description: String = "", optional: Boolean = false, constraint: String = "", ignored: Boolean = false) {
        val descriptor = RequestDocumentation.parameterWithName(fieldName).description(description)
        addOptions(descriptor, optional, constraint, ignored)
        descriptors.add(descriptor)
    }

    fun queryParameterSnippet(): Snippet {
        if (pageable) {
            return RequestDocumentation.queryParameters(descriptors + PAGE_PARAMS)
        }
        return RequestDocumentation.queryParameters(descriptors)
    }

    fun pathParameterSnippet(): Snippet {
        return RequestDocumentation.pathParameters(descriptors)
    }

    companion object {
        private val PAGE_PARAMS: List<ParameterDescriptor> = listOf(
            RequestDocumentation.parameterWithName("page").description("요청 페이지 번호\n\n기본값: 0").optional(),
            RequestDocumentation.parameterWithName("size").description("요청 페이지 크기\n\n기본값: 20\n\n최대값: 100").optional()
        )
    }
}

package com.sat.study.common.documentation.dsl

import com.megazone.act.cms.common.documentation.dsl.AbstractSnippetDsl
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.snippet.Snippet
import kotlin.reflect.KClass

@RestDocsMarker
class BodySnippetDsl : AbstractSnippetDsl() {
    private val descriptors: MutableList<FieldDescriptor> = mutableListOf()
    var pageable: Boolean = false

    fun <T : Enum<T>> field(fieldName: String, description: String = "", optional: Boolean = false, constraint: KClass<T>, ignored: Boolean = false) {
        field(fieldName, description, optional, getConstraintsText(constraint), ignored)
    }

    fun field(fieldName: String, description: String = "", optional: Boolean = false, constraint: String = "", ignored: Boolean = false) {
        val descriptor = fieldWithPath(fieldName).description(description)
        addOptions(descriptor, optional, constraint, ignored)
        descriptors.add(descriptor)
    }

    fun requestSnippet(): Snippet {
        return requestFields(descriptors)
    }

    fun responseSnippet(): Snippet {
        if (pageable) {
            return responseFields(descriptors + PAGE_FIELDS)
        }
        return responseFields(descriptors)
    }

    companion object {
        private val PAGE_FIELDS: List<FieldDescriptor> = listOf(
            fieldWithPath("pageable.pageNumber").description("요청 페이지 번호"),
            fieldWithPath("pageable.pageSize").description("요청 페이지 크기"),
            fieldWithPath("pageable.sort.empty").description(""),
            fieldWithPath("pageable.sort.unsorted").description("요청 정렬 여부"),
            fieldWithPath("pageable.sort.sorted").description("요청 정렬 여부"),
            fieldWithPath("pageable.offset").description("요청 오프셋"),
            fieldWithPath("pageable.paged").description("페이징 여부"),
            fieldWithPath("pageable.unpaged").description("페이징 여부"),
            fieldWithPath("totalPages").description("전체 페이지 수"),
            fieldWithPath("totalElements").description("전체 데이터 수"),
            fieldWithPath("last").description("마지막 페이지 여부"),
            fieldWithPath("size").description("현재 페이지 크기"),
            fieldWithPath("number").description("현재 페이지 수"),
            fieldWithPath("sort.empty").description("정렬 여부"),
            fieldWithPath("sort.unsorted").description("정렬 여부"),
            fieldWithPath("sort.sorted").description("정렬 여부"),
            fieldWithPath("first").description("첫번째 페이지 여부"),
            fieldWithPath("numberOfElements").description("현재 페이지의 데이터 수"),
            fieldWithPath("empty").description("현재 페이지가 비어있는지 여부")
        )
    }
}

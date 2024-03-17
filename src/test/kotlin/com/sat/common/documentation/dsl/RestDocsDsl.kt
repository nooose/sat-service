package com.sat.common.documentation.dsl

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.ResultHandler

@DslMarker
annotation class RestDocsMarker

fun ResultActionsDsl.andDocument(init: RestDocsDsl.() -> Unit) {
    val restDocsDsl = RestDocsDsl()
    restDocsDsl.init()
    andDo { handle(restDocsDsl.handler()) }
}

@RestDocsMarker
class RestDocsDsl {
    var tag: String? = null
    var summary: String? = null
    var description: String? = null
    private val snippets: MutableList<Snippet> = mutableListOf()

    fun pathVariables(init: ParameterSnippetDsl.() -> Unit) {
        val parameters = ParameterSnippetDsl()
        parameters.init()
        snippets.add(parameters.pathParameterSnippet())
    }

    fun queryParams(init: ParameterSnippetDsl.() -> Unit) {
        val parameters = ParameterSnippetDsl()
        parameters.init()
        snippets.add(parameters.queryParameterSnippet())
    }

    fun requestParts(init: PartSnippetDsl.() -> Unit) {
        val part = PartSnippetDsl()
        part.init()
        snippets.add(part.snippet())
    }

    fun requestBody(init: BodySnippetDsl.() -> Unit) {
        val responseBody = BodySnippetDsl()
        responseBody.init()
        snippets.add(responseBody.requestSnippet())
    }

    fun responseHeaders(init: HeaderSnippetDsl.() -> Unit) {
        val header = HeaderSnippetDsl()
        header.init()
        snippets.add(header.responseHeaderSnippet())
    }

    fun responseBody(init: BodySnippetDsl.() -> Unit) {
        val responseBody = BodySnippetDsl()
        responseBody.init()
        snippets.add(responseBody.responseSnippet())
    }

    fun handler(): ResultHandler {
        val resourceDetails = MockMvcRestDocumentationWrapper.resourceDetails()
            .tag(tag ?: "")
            .summary(summary)
            .description(description ?: summary)
        return MockMvcRestDocumentationWrapper.document(identifier = "{class-name}/{method-name}", resourceDetails = resourceDetails, snippets = snippets.toTypedArray())
    }
}

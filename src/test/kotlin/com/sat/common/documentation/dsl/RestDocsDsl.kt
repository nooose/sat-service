package com.sat.common.documentation.dsl

import BearerHeaderExtractor
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.Schema.Companion.schema
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.ResultHandler
import kotlin.reflect.KClass

@DslMarker
annotation class RestDocsMarker

fun ResultActionsDsl.andDocument(init: RestDocsDsl.() -> Unit) {
    val restDocsDsl = RestDocsDsl()
    restDocsDsl.init()
    val extractor = BearerHeaderExtractor()
    andDo { handle(extractor) }
    andDo { handle(restDocsDsl.handler(extractor)) }
}

@RestDocsMarker
class RestDocsDsl {
    var tag: String? = null
    var summary: String? = null
    var description: String? = null
    private var requestType: KClass<*>? = null
    private var responseType: KClass<*>? = null

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
        requestType = responseBody.type
        snippets.add(responseBody.requestSnippet())
    }

    fun requestHeaders(init: HeaderSnippetDsl.() -> Unit) {
        val header = HeaderSnippetDsl()
        header.init()
        snippets.add(header.requestHeaderSnippet())
    }

    fun responseHeaders(init: HeaderSnippetDsl.() -> Unit) {
        val header = HeaderSnippetDsl()
        header.init()
        snippets.add(header.responseHeaderSnippet())
    }

    fun responseBody(init: BodySnippetDsl.() -> Unit) {
        val responseBody = BodySnippetDsl()
        responseBody.init()
        responseType = responseBody.type
        snippets.add(responseBody.responseSnippet())
    }

    fun handler(previousHandler: ResultHandler): ResultHandler {
        if (previousHandler is BearerHeaderExtractor && previousHandler.hasBearer()) {
            val authHeader = HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
            snippets.add(HeaderDocumentation.requestHeaders(authHeader))
        }

        val resourceDetails = MockMvcRestDocumentationWrapper.resourceDetails()
            .tag(tag ?: "")
            .summary(summary)
            .description(description ?: summary)
        if (requestType != null) {
            resourceDetails.requestSchema(schema(requestType!!.simpleName!!))
        }
        if (responseType != null) {
            resourceDetails.responseSchema(schema(responseType!!.simpleName!!))
        }

        return MockMvcRestDocumentationWrapper.document(identifier = "{class-name}/{method-name}", resourceDetails = resourceDetails, snippets = snippets.toTypedArray())
    }
}

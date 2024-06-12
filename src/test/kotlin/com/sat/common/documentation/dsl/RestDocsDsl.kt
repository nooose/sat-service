package com.sat.common.documentation.dsl

import BearerHeaderExtractor
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper
import com.epages.restdocs.apispec.ResourceSnippetDetails
import com.epages.restdocs.apispec.Schema.Companion.schema
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.ResultHandler
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

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
    var deprecated: Boolean = false
    var requestTypeString: String? = null
    var responseTypeString: String? = null

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

    inline fun <reified T> requestBody(init: BodySnippetDsl.() -> Unit) {
        val body = BodySnippetDsl()
        body.init()
        requestTypeString = simplifyType(typeOf<T>())
        addSnippet(body.requestSnippet())
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

    inline fun <reified T> responseBody(init: BodySnippetDsl.() -> Unit) {
        val body = BodySnippetDsl()
        body.init()
        responseTypeString = simplifyType(typeOf<T>())
        addSnippet(body.responseSnippet())
    }

    fun simplifyType(type: KType): String {
        return when (val classifier = type.classifier) {
            is KClass<*> -> {
                val typeName = classifier.simpleName
                val arguments = type.arguments
                if (arguments.isNotEmpty()) {
                    val argumentTypes = arguments.joinToString(", ") { arg ->
                        arg.type?.let { simplifyType(it) } ?: "*"
                    }
                    "$typeName<$argumentTypes>"
                } else {
                    typeName ?: "Unknown"
                }
            }
            else -> "Unknown"
        }
    }

    fun addSnippet(snippet: Snippet) {
        snippets.add(snippet)
    }

    fun handler(previousHandler: ResultHandler): ResultHandler {
        if (previousHandler is BearerHeaderExtractor && previousHandler.hasBearer()) {
            val authHeader = HeaderDocumentation.headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer 토큰")
            snippets.add(HeaderDocumentation.requestHeaders(authHeader))
        }

        return MockMvcRestDocumentationWrapper.document(
            identifier = "{class-name}/{method-name}",
            resourceDetails = makeResourceDetails(),
            snippets = snippets.toTypedArray()
        )
    }

    private fun makeResourceDetails(): ResourceSnippetDetails {
        val resourceDetails = MockMvcRestDocumentationWrapper.resourceDetails()
            .tag(tag ?: "")
            .summary(summary)
            .description(description ?: summary)
        requestTypeString?.let { type -> resourceDetails.requestSchema(schema(type)) }
        responseTypeString?.let { type -> resourceDetails.responseSchema(schema(type)) }
        if (deprecated) {
            resourceDetails.deprecated(true)
        }
        return resourceDetails
    }
}

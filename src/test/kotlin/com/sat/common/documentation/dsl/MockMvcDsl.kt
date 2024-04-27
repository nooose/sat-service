package com.sat.common.documentation.dsl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.generate.RestDocumentationGenerator
import org.springframework.test.web.servlet.*

fun MockMvc.GET(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return get(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

fun MockMvc.POST(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return post(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

fun MockMvc.PUT(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return put(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

fun MockMvc.PATCH(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return patch(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

fun MockMvc.DELETE(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return delete(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

fun MockMvc.MULTIPART(
    urlTemplate: String,
    vararg urlVariables: Any?,
    dsl: MockMultipartHttpServletRequestDsl.() -> Unit = {}
): ResultActionsDsl {
    return multipart(urlTemplate, *urlVariables) {
        requestAttr(RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE, urlTemplate)
        dsl()
    }
}

private const val JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxIiwibmFtZSI6Iu2Zjeq4uOuPmSIsImlhdCI6MTUxNjIzOTAyMn0.tIMZuSy5djyBIM-kUnYOiOrSCCmXA6rgXLZO3gD7Uvs"
fun MockHttpServletRequestDsl.bearerToken() {
    header(HttpHeaders.AUTHORIZATION, "Bearer $JWT")
}

private val objectMapper: ObjectMapper = ObjectMapper()
    .registerKotlinModule()
    .registerModule(JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
fun MockHttpServletRequestDsl.content(request: Any) {
    contentType = MediaType.APPLICATION_JSON
    characterEncoding = "utf-8"
    content = objectMapper.writeValueAsString(request)
}

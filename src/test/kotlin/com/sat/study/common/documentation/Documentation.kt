package com.sat.study.common.documentation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.http.HttpDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(RestDocumentationExtension::class)
abstract class Documentation {
    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(context: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(
                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                    .snippets().withDefaults(HttpDocumentation.httpRequest(), HttpDocumentation.httpResponse())
                    .and()
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.modifyHeaders().remove("Host"), Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.modifyHeaders().remove("Date"), Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.modifyHeaders().remove("Vary"), Preprocessors.prettyPrint())
            )
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .addFilters<DefaultMockMvcBuilder>()
            .build()
    }
}

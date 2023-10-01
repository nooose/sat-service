package com.sat.documentation;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Attributes.Attribute;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyHeaders;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(RestDocumentationExtension.class)
abstract public class Documentation {

    protected static final String BEARER_JWT = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Im5vb3NlIiwiaWF0IjoxNTE2MjM5MDIyfQ.gq_ho92Z170Gf0KO877mWjfTwlHfvvsSGttWfYbUdfE";

    protected MockMvc mockMvc;

    protected static Attribute constraintsAttribute(String text) {
        return key("constraints").value(text);
    }

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(documentationConfiguration(restDocumentation)
                .snippets().withDefaults(httpRequest(), httpResponse())
                .and()
                .operationPreprocessors()
                .withRequestDefaults(modifyHeaders().remove("Host"), prettyPrint())
                .withResponseDefaults(modifyHeaders().remove("Date"), prettyPrint())
                .withResponseDefaults(modifyHeaders().remove("Vary"), prettyPrint())
            )
            .alwaysDo(print())
            .addFilters()
            .build();
    }

    protected RestDocumentationResultHandler document(Snippet... snippets) {
        return document("", snippets);
    }

    protected RestDocumentationResultHandler document(String description, Snippet... snippets) {
        return document(description, "", snippets);
    }

    protected RestDocumentationResultHandler document(String description, String summary, Snippet... snippets) {
        return MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}", description, summary, snippets);
    }
}

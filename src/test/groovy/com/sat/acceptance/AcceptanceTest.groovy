package com.sat.acceptance

import com.sat.config.TestSecurityConfig
import com.sat.utils.CustomRequestLoggingFilter
import com.sat.utils.CustomResponseLoggingFilter
import com.sat.utils.DataLoader
import com.sat.utils.DatabaseCleaner
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationExtension
import spock.lang.Specification

@Import(TestSecurityConfig.class)
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private DatabaseCleaner databaseCleaner

    @Autowired
    private DataLoader dataLoader

    protected RequestSpecification spec

    def setup() {
        RestAssured.port = port
        databaseCleaner.execute()
        dataLoader.loadData()
        spec = new RequestSpecBuilder()
                .addFilter(new CustomRequestLoggingFilter())
                .addFilter(new CustomResponseLoggingFilter())
                .build()
    }
}

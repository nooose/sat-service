package com.sat

import com.sat.config.TestSecurityConfig
import com.sat.utils.DataLoader
import com.sat.utils.DatabaseCleaner
import io.restassured.RestAssured
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import spock.lang.Specification

@Import(TestSecurityConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AcceptanceTest extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    private DatabaseCleaner databaseCleaner

    @Autowired
    private DataLoader dataLoader

    def setup() {
        RestAssured.port = port
        databaseCleaner.execute()
        dataLoader.loadData()
    }
}

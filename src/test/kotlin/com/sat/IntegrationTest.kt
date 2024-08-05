package com.sat

import io.kotest.core.spec.AfterTest
import io.kotest.core.spec.Spec
import io.kotest.core.test.isRootTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class TestEnvironment

@Import(TestConfig::class)
@TestEnvironment
@SpringBootTest
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class IntegrationTest

fun Spec.afterRootTest(f: AfterTest) {
    afterTest {
        val (testcase) = it
        if (testcase.isRootTest()) f(it)
    }
}

package com.sat

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

const val KEY = "16c9799aaf5b0b3e94524c8ebf3d53f6e63b2117946576c3eb6a1e1d1dd80333"

@ActiveProfiles("test")
@SpringBootTest(properties = ["jwt.secret-key=${KEY}"])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SpringBootTestWithProfile

package com.sat

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

const val KEY = "16c9799aaf5b0b3e94524c8ebf3d53f6e63b2117946576c3eb6a1e1d1dd80333"

@SpringBootTest(properties = ["jwt.secret-key=${KEY}"])
class SatApplicationTests {

	@Test
	fun contextLoads() {
	}

}

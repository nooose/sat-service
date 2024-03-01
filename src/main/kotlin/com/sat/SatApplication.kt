package com.sat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SatApplication

fun main(args: Array<String>) {
	runApplication<SatApplication>(*args)
}

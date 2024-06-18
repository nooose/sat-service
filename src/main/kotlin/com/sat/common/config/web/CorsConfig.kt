package com.sat.common.config.web

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration(proxyBeanMethods = false)
class CorsConfig(
    @Value("\${external.frontend.url}")
    private val frontendUrl: String,
) {

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.allowCredentials = true
        corsConfig.allowedOrigins = listOf(frontendUrl)
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.exposedHeaders = listOf("*")

        val corsConfigSource = UrlBasedCorsConfigurationSource()
        corsConfigSource.registerCorsConfiguration("/**", corsConfig)
        return corsConfigSource
    }
}

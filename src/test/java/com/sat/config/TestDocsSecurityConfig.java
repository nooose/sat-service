package com.sat.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@TestConfiguration
public class TestDocsSecurityConfig {

    @Bean
    public WebSecurityCustomizer testDocsConfigure() {
        return web -> web.ignoring()
            .requestMatchers(AntPathRequestMatcher.antMatcher("/**"));
    }
}

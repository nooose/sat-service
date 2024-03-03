package com.sat.common.config.jpa

import com.sat.common.utils.principal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@EnableJpaAuditing
@Configuration(proxyBeanMethods = false)
class JpaConfig {

    @Bean
    fun auditorAware(): AuditorAware<Long> {
        return AuditorAware {
            val principal = SecurityContextHolder.getContext().principal()
            Optional.ofNullable(principal.id)
        }
    }
}

package com.sat.common.config.jpa

import com.sat.security.AuthenticatedMember
import org.hibernate.cfg.AvailableSettings
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
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
            val principal = SecurityContextHolder.getContext().authentication?.principal as? AuthenticatedMember
            Optional.ofNullable(principal?.id ?: 0L)
        }
    }

    @Bean
    fun functionLogCustomizer(): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer {
            it[AvailableSettings.STATEMENT_INSPECTOR] = CustomSqlCommenter()
        }
    }
}

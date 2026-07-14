package org.vendethiel.librarymgr.library.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.session.ReactiveSessionRegistry
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.session.MapSessionRepository
import java.util.concurrent.ConcurrentHashMap

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun userDetailsService(): UserDetailsService {
        val users = User.withDefaultPasswordEncoder()
        val manager = InMemoryUserDetailsManager()
        manager.createUser(users.username("user").password("password").roles("USER").build())
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build())
        return manager
    }

    @Bean
    @Order(1)
    fun adminFilter(http: HttpSecurity): SecurityFilterChain {
        http {
            securityMatcher("/admin/**")
            csrf { disable() }
            httpBasic { }
            authorizeHttpRequests {
                authorize(anyRequest, hasRole("ADMIN"))
            }
        }
        return http.build()
    }

    @Bean
    fun mainFilter(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            httpBasic {}
            formLogin {}
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }

    @Bean
    fun mapSessionRegistry(): MapSessionRepository {
        return MapSessionRepository(ConcurrentHashMap())
    }
}
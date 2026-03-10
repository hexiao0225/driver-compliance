package com.compliance.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        val frontendUrl = System.getenv("FRONTEND_URL")
        val allowedOrigins = buildList {
            add("http://localhost:3000")
            add("http://localhost:5173")
            if (!frontendUrl.isNullOrBlank()) add(frontendUrl)
        }.toTypedArray()

        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins(*allowedOrigins)
                    .allowedMethods("GET", "POST", "OPTIONS")
                    .allowedHeaders("*")
            }
        }
    }
}

package com.henilveira.sonita.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 🔹 Aplica a configuração para todos os endpoints
                        .allowedOrigins("localhost:3000") // 🔹 Substitua pelo domínio do seu frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 🔹 Métodos permitidos
                        .allowedHeaders("*") // 🔹 Headers permitidos
                        .allowCredentials(true); // 🔹 Permite cookies e credenciais
            }
        };
    }
}
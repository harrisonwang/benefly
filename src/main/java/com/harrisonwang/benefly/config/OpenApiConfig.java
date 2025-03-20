package com.harrisonwang.benefly.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI beneflyOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Benefly Announcement API")
                        .description("REST API for managing announcements")
                        .version("1.0.0"));
    }
}

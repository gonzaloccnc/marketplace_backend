package dev.pe.app.v1.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private SecurityScheme creteApiKeyScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("Bearer");
  }

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(
            new SecurityRequirement()
                .addList("Bearer Authentication")
        )
        .components(
            new Components()
                .addSecuritySchemes("Bearer Authentication", creteApiKeyScheme())
        )
        .info(
            new Info()
                .title("My REST API SPRING BOOT - PRACTICE")
                .description("")
                .version("1.0")
                .contact(
                    new Contact()
                        .name("Gonzalo Manco")
                        .email("gonzalodevelw@gmail.com")
                        .url("https://gonzaloccnc.github.io/portfolio/")
                )
                .license(
                    new License()
                        .name("This projects is for practice in spring boot deep with security")
                )
        );
  }
}

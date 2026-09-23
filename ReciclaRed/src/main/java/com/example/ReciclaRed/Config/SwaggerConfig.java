package com.example.ReciclaRed.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // 1. Información general de la API que aparecerá en el encabezado
                .info(new Info()
                        .title("API REST - ReciclaRed")
                        .version("1.0.0")
                        .description("Documentación interactiva y especificación técnica de los endpoints operativos y transversales del proyecto ReciclaRed."))

                // 2. Exigencia global de seguridad para probar las rutas desde la interfaz
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                // 3. Definición estructural del esquema criptográfico (Token Bearer JWT)
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}
package com.openclassrooms.mddapi.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    private final OpenAPIProperties openAPIProperties;

    public OpenAPIConfiguration(OpenAPIProperties openAPIProperties) {
        this.openAPIProperties = openAPIProperties;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url(openAPIProperties.getDevUrl())
                                .description("MDD API – environnement de développement"),
                        new Server()
                                .url(openAPIProperties.getProdUrl())
                                .description("MDD API – environnement de production")
                ))
                .info(new Info()
                        .title("MDD API")
                        .version("1.0.0")
                        .description("""
                    API backend de MDD (Monde de Dév).
                    Permet la gestion des utilisateurs, des thèmes (topics),
                    des articles (posts) et des commentaires.
                    Les utilisateurs peuvent s’abonner à des topics
                    et consulter un fil d’actualité propre.
                    """))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }
}

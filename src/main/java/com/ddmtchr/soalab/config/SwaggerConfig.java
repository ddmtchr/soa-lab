package com.ddmtchr.soalab.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi dragonsApi() {
        return GroupedOpenApi.builder()
                .group("Dragons & Caves")
                .pathsToMatch("/api/v1/dragon/**", "/api/v1/cave/**")
                .addOpenApiCustomizer(openApi -> {
                            Server server = new Server();
                            server.setUrl("http://localhost:9876/soa");
                            server.setDescription("Dragons and caves server");
                            openApi.servers(List.of(server))
                                    .info(new Info()
                                            .title("Dragons & Caves API")
                                            .description("Dragons & Caves API documentation")
                                            .version("1.0"));
                        }
                )
                .build();
    }

    @Bean
    public GroupedOpenApi killersApi() {
        return GroupedOpenApi.builder()
                .group("Persons & Teams")
                .pathsToMatch("/api/v1/killer/**", "/api/v1/person/**", "/api/v1/team/**")
                .addOpenApiCustomizer(openApi -> {
                    Server server = new Server();
                    server.setUrl("http://localhost:9877/soa");
                    server.setDescription("Persons and teams server");
                    openApi.servers(List.of(server))
                            .info(new Info()
                                    .title("Persons & Teams API")
                                    .description("Persons & Teams API documentation")
                                    .version("1.0"));
                })
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Soa Lab API")
                        .version("1.0")
                        .description("Soa Lab API documentation"));
    }
}

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
                .pathsToMatch("/dragons/**", "/caves/**")
                .addOpenApiCustomizer(openApi -> {
                            Server server = new Server();
                            server.setUrl("http://localhost:9876/soa/api/v1");
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
                .pathsToMatch("/killer/**", "/persons/**", "/teams/**")
                .addOpenApiCustomizer(openApi -> {
                    Server server = new Server();
                    server.setUrl("http://localhost:9877/soa/api/v1");
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

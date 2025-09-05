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
                .group("Dragons")
                .pathsToMatch("/api/v1/dragon/**")
                .addOpenApiCustomizer(openApi -> {
                            Server server = new Server();
                            server.setUrl("https://localhost:9876/api/v1");
                            server.setDescription("Dragons server");
                            openApi.servers(List.of(server))
                                    .info(new Info()
                                            .title("Dragons API")
                                            .description("Dragons API documentation")
                                            .version("1.0"));
                        }
                )
                .build();
    }

    @Bean
    public GroupedOpenApi killersApi() {
        return GroupedOpenApi.builder()
                .group("Killers")
                .pathsToMatch("/api/v1/killer/**")
                .addOpenApiCustomizer(openApi -> {
                    Server server = new Server();
                    server.setUrl("https://localhost:9877/api/v1");
                    server.setDescription("Killers server");
                    openApi.servers(List.of(server))
                            .info(new Info()
                                    .title("Killers API")
                                    .description("Killers API documentation")
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

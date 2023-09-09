package com.eventsvk.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:info.properties")
@AllArgsConstructor
public class SwaggerConfig {

    private final Environment env;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()
                                .title(env.getProperty("info.title"))
                                .version(env.getProperty("info.version"))
                                .description(env.getProperty("info.description"))
                                .license(new License().name("Apache 2.0").url("https://springdoc.org"))
                                .contact(
                                        new Contact()
                                                .email(env.getProperty("info.email"))
                                                .url(env.getProperty("info.url"))
                                                .name(env.getProperty("info.name"))
                                )
                );
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("controllers")
                .packagesToScan("com.eventsvk.controllers")
                .build();
    }

}
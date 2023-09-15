package com.eventsvk.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition
@PropertySource("classpath:info.properties")
public class SwaggerConfig {

    @Value("${info.title}")
    private String TITLE;
    @Value("${info.version}")
    private String VERSION;
    @Value("${info.description}")
    private String DESCRIPTION;
    @Value("${info.licence}")
    private String LICENSE;
    @Value("${info.title}")
    private String EMAIL;
    @Value("${info.url}")
    private String URL;
    @Value("${info.name}")
    private String NAME;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(
                        new Info()

                                .title(TITLE)
                                .version(VERSION)
                                .description(DESCRIPTION)
                                .license(new License().name("Apache 2.0").url(LICENSE))
                                .contact(
                                        new Contact()
                                                .email(EMAIL)
                                                .url(URL)
                                                .name(NAME)
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

    @Bean
    public GroupedOpenApi actuatorApi() {
        return GroupedOpenApi.builder()
                .group("actuator")
                .pathsToMatch("/actuator/**")
                .pathsToExclude("/actuator/health/*")
                .build();
    }
}
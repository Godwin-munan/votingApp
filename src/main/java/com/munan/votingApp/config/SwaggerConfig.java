package com.munan.votingApp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Voting", version = "1.0", description = "Voting API"))
@Configuration
public class SwaggerConfig {



}
package com.hhernaar.telephone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI(@Value("${service.contexto}") String context) {
    return new OpenAPI().info(this.getInfo());
  }

  private Info getInfo() {
    return new Info().title("TELEPHONE SERVICE").description("Demo REST Service").version("v1")
        .contact(
            new Contact().url("https://github.com/hhernaar/telephone-service").name("HHERNAAR"))
        .description("API demo to show REST service basic functions.");
  }



}

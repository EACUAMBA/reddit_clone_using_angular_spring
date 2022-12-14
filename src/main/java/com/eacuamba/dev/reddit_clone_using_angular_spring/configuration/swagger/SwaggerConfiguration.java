package com.eacuamba.dev.reddit_clone_using_angular_spring.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket redditCloneApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/api/")
                .apiInfo(this.getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("Reddit Clone API")
                .description("API for Reddit Clone Application")
                .contact(new Contact("Edilson Alexandre Cuamba", "https://www.eacuamba.com", "edilsoncuamba@gmail.com"))
                .license("Apache License Version 2.3")
                .version("1.0")
                .build();
    }
}

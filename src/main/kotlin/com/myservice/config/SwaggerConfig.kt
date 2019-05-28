package com.myservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux


@Configuration
@EnableSwagger2WebFlux
class Swagger2Config {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.myservice.controller"))
                .paths(PathSelectors.regex("/.*"))
                .build().apiInfo(apiEndPointsInfo())
    }

    private fun apiEndPointsInfo(): ApiInfo {
        return ApiInfoBuilder().title("My Service")
                .description("Person management REST API")
                .contact(Contact("Dummy", "www.dummy.net", "dummy@dummy.com"))
                .version("1.0.0")
                .build()
    }
}
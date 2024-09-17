package com.pramati.crs.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author srinivasn
 *  
 *  Enables the swaggers api
 */
@Configuration
@EnableSwagger2
public class SweggerConfig {

	@Bean
	public Docket api() {
		ArrayList<Integer> a = new ArrayList<Integer>(10);
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors
				.basePackage("com.pramati.crs.controller"))
	            .paths(PathSelectors.regex("/.*"))
	            .build().apiInfo(apiEndPointsInfo());
	
	}

	private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Profile-Service Rest API")
                .description("Profile service rest api").contact("srinivas.")
                .version("1.0.0")
                .build();
	}
	
	
}

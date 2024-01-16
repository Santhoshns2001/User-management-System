package com.User.ums.Utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDocumentation {
	
	Contact contact() {
		return new Contact().name("N S Santosh Kumar")
				.url("")
				.email("");
	}
	
	Info info () {
		return new Info().title("User Management Systsem API")
				.version("1.0v")
				.description("User Management System is a RESTFUL API built using"+
						"Spring Boot and MySql database");
	}
	
	@Bean
	OpenAPI openAPI() {
		return new OpenAPI().info(info());
	}
}

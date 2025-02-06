package com.codesimple.bookstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		final String securitySchemeName = "basicAuth";

		Components components = new Components();

		ArrayList<SecurityScheme> listSecurityScheme = new ArrayList<SecurityScheme>();
		//listSecurityScheme.add(getBasicSecurityScheme(securitySchemeName));
		listSecurityScheme.add(getBearerSecurityScheme());

		return new OpenAPI().info(getInfo()).externalDocs(getExternalDocumentation())
				.addSecurityItem(getSecurityRequirement(securitySchemeName))
				.components(getComponents(components, securitySchemeName, listSecurityScheme));
	}

	private Info getInfo() {
		Info info = new Info().title("Authentication Service")
				.description("This is auth service use for validate the user.").version("v1.0.0")
				.license(new License().name("Apache 2.0").url("http://springdoc.org"));

		return info;
	}

	private ExternalDocumentation getExternalDocumentation() {
		ExternalDocumentation externalDocumentation = new ExternalDocumentation()
				.description("SpringBoot Wiki Documentation").url("https://springboot.wiki.github.org/docs");
		return externalDocumentation;
	}

	private SecurityRequirement getSecurityRequirement(String securitySchemeName) {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);
		return securityRequirement;
	}

	private SecurityScheme getBasicSecurityScheme(String securitySchemeName) {
		SecurityScheme securityScheme = new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP)
				.scheme("basic");
		return securityScheme;
	}
	
	private SecurityScheme getBearerSecurityScheme() {
	    return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	        .bearerFormat("JWT")
	        .scheme("bearer");
	}

	/*
	 * private Components getComponents(Components components, String
	 * securitySchemeName) { components.addSecuritySchemes(securitySchemeName,
	 * getSecurityScheme(securitySchemeName)); return components; }
	 */
	
	private Components getComponents(Components components, String securitySchemeName, ArrayList<SecurityScheme> listSecurityScheme) {
		listSecurityScheme.forEach(t -> components.addSecuritySchemes(securitySchemeName, t));
		return components;
	}
}
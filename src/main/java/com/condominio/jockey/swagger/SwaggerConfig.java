package com.condominio.jockey.swagger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.models.auth.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2)
//				informacion del api
				.apiInfo(userApiInfo()).select().
//				todas las rutas
				paths(PathSelectors.any())
//				buscar los controller
				.apis(RequestHandlerSelectors.basePackage("com.condominio.jockey.controller")).build()
//				desactivo mensajes de respuesta por defecto
				.useDefaultResponseMessages(false)
//				contexto del envio y retorno de la informacion
				.produces(contextType()).consumes(contextType())
//				protocolos de solicitud
				.protocols(protocolos())
//				mensajes globales para get
				.globalResponseMessage(RequestMethod.GET, responseMessageForGET())
//				indico que requiere de token
				.securitySchemes(
						Arrays.asList(new ApiKey("Token de acceso", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
				.securityContexts(Arrays.asList(securityContext()));
	}

	private Set<String> protocolos() {
		Set<String> protocolo = new HashSet<>();
		protocolo.add("http");
		protocolo.add("https");
		return protocolo;
	}

	private ApiInfo userApiInfo() {
		Contact contact = new Contact("Kato", "http://www.kato.com", "speed21@outlook.com");
		return new ApiInfoBuilder().contact(contact).title("Jockey Club").version("1.0")
				.termsOfServiceUrl("TERMS OF SERVICE URL").license("Apache License Version 2.0")
				.description("Conexión a MongoDB (jockey)").build();
	}

	private Set<String> contextType() {
		Set<String> context = new HashSet<>();
		context.add("application/json");
		return context;
	}

	private List<ResponseMessage> responseMessageForGET() {
		return new ArrayList<ResponseMessage>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			{
				add(new ResponseMessageBuilder().code(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
						.message("ERROR INTERNO EN EL SERVIDOR").build());
				add(new ResponseMessageBuilder().code(HttpServletResponse.SC_FORBIDDEN).message("ACCESO DENEGADO")
						.build());
			}
		};
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("ROL_ADMIN", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Token de acceso", authorizationScopes));
	}
}
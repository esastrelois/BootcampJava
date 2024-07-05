package com.proyectoUno;

import java.util.TreeMap;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import com.proyectoUno.domains.contracts.proxies.CalculatorProxy;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@EnableDiscoveryClient
@EnableFeignClients("com.proyectoUno.application.proxies")
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: Catalogo de peliculas",
                version = "1.0",
                description = "Ejemplo de Microservicio utilizando la base de datos **Sakila**.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20230911")
)
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	OpenApiCustomizer sortSchemasAlphabetically() {
		return openApi -> {
			var schemas = openApi.getComponents().getSchemas();
			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
		};
	}
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
	}
	@Bean
	@Primary
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplateLB(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@Bean
	CommandLineRunner lookup(CalculatorProxy client) {
		return args -> {		
			System.err.println("Suma --> " + client.add(8, 2));
			System.err.println("Resta --> " + client.sub(8, 2));
			System.err.println("Multiplicacion --> " + client.mult(8, 2));
			System.err.println("Division --> " + client.div(8, 2));
		};
	}
}

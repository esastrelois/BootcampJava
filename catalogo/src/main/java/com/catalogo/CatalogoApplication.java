package com.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.catalogo.domains.contrats.services.FilmService;

@SpringBootApplication
public class CatalogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
	}
}

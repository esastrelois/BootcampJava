package com.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.models.FilmDTO;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	
	@Autowired
	FilmService frv;
	
	@Autowired
	FilmRepository dao;
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		System.out.println("Películas:");
		frv.getByProjection(FilmDTO.class).forEach(System.out::println);
		
		System.out.println("Película 20:");
		var item = dao.findById(20);
		if(item.isEmpty()) {
			System.err.println("No encontrado");
		} else {
			System.out.println(item.get());
		}
	}
}

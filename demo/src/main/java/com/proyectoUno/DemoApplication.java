package com.proyectoUno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.proyectoUno.domains.contracts.repositories.ActorRepository;
import com.proyectoUno.domains.entities.Actor;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		dao.findAll().forEach(System.out::println);
//		var item = dao.findById(301);
//		if(item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			System.out.println(item.get());
//		}
//		var actor = new Actor(0, "Pepito", "Grillo");
//		System.out.println(dao.save(actor));
//		var item = dao.findById(201);
//		if(item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			var actor = item.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//		dao.deleteById(201);
//		dao.findAll().forEach(System.out::println);
		dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("P").forEach(System.out::println);
		dao.findTop5ByLastNameStartingWith("P", Sort.by("LastName").ascending()).forEach(System.out::println);
		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
		dao.findByJPQL(200).forEach(System.out::println);
		dao.findBySQL(200).forEach(System.out::println);
		//Al hacer extender ActorRepository de JpaSpecificationExecutor<Actor> se puede utilizar el builder de la siguiente manera para realizar lo mismo
		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"),200)).forEach(System.out::println);
		//Actores con id menor de 10
		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"),10)).forEach(System.out::println);
	}
	
	/*
	@Autowired
//	@Qualifier("es")
	Saluda saluda;
	@Autowired
//	@Qualifier("en")
	Saluda saluda2;
	@Autowired
	Entorno entorno;
	@Autowired
	private Rango rango;

//	@Autowired(required = false)
//	SaludaEnImpl kk;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		var saluda = new Saluda();
		System.out.println(saluda.getContador());
		saluda.saluda("Mundo");
//		saluda.saluda(null);
		saluda2.saluda("Mundo");
		System.out.println(saluda.getContador());
		System.out.println(saluda2.getContador());
		System.out.println(entorno.getContador());
		System.out.println(rango.getMin() + " -> " + rango.getMax());
	}
	*/
}

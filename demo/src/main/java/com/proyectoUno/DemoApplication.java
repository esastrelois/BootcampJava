package com.proyectoUno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.proyectoUno.domains.contracts.proxies.CalculatorProxy;



@SpringBootApplication
public class DemoApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	//PRUEBAS 3 - Utilizando servicios
//	@Autowired
//	ActorService srv;
	
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		srv.getByProjection(ActorDTO.class).forEach(System.out::println);
	}
	
	
	
	// PRUEBAS 2 - Utilizando repositorios
	@Autowired
//	ActorRepository dao;
/*	
	@Override
	@Transactional //Forma 2 - para mantener la conexión abierta y que traiga toda la información de las películas siempre
	//Si ponemos el @Transactional no es necesario el FetchType.EAGER en el atributo filmActors de Actor.java
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
		
//		/* Prueba - Uso de metodos JPA, JPQL y SQL */		
//		dao.deleteById(201);
//		dao.findAll().forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWith("P", Sort.by("LastName").ascending()).forEach(System.out::println);
//		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.findByJPQL(200).forEach(System.out::println);
//		dao.findBySQL(200).forEach(System.out::println);
//		//Al hacer extender ActorRepository de JpaSpecificationExecutor<Actor> se puede utilizar el builder de la siguiente manera para realizar lo mismo
//		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"),200)).forEach(System.out::println);
//		//Actores con id menor de 10
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"),10)).forEach(System.out::println);
//		var item = dao.findById(1);
		
//		/* Prueba - Carga apresurada vs carga perezosa */
//		if(item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(f -> System.out.println(f.getFilm().getTitle()));
//		}
		
		/* Prueba - Meter datos inválidos */
//		var actor = new Actor(0, " ", null);
//		if(actor.isValid()) {
//			System.out.println(dao.save(actor));
//		}else {
//			actor.getErrors().forEach(System.out::println);
//		}
		
		/* Pruebas de proyecciones */
		/* Sin proyecciones - necesitas un metodo para devolver cada tipo de dato */
//		var actor = new ActorDTO(0, "FROM", "DTO");
//		dao.save(ActorDTO.from(actor));
//		dao.findAll().forEach(item -> System.out.println(ActorDTO.from(item)));
//		System.out.println("Sin proyeccion");
//		dao.readByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.queryByActorIdGreaterThanEqual(200).forEach(item -> System.out.println(item.getId()+" "+item.getNombre()));
//		
//		/* Con proyeccion - permite usar el mismo metodo y pasarle el tipo de objeto diferente en cada caso */
//		System.out.println("Con proyeccion DTO");
//		dao.findByActorIdGreaterThanEqual(200, ActorDTO.class).forEach(System.out::println);
//		System.out.println("Con proyeccion Short");
//		dao.findByActorIdGreaterThanEqual(200, ActorShort.class).forEach(item -> System.out.println(item.getId()+" "+item.getNombre()));
//	
		
		/* Paginación */
		//Muestra la cuarta página (3), de 10 en 10 resultados, ordenado por ActorId
//		dao.findAll(PageRequest.of(3, 10, Sort.by("ActorId"))).forEach(System.out::println);

		/* Serialización */
//		var serializa = new ObjectMapper(); //var serializa = new XmlMapper(); //Otra forma sería serializar en XML
//		dao.findAll(PageRequest.of(3,  10, Sort.by("ActorId")))
//			.forEach(item -> {
//				try {
//					System.out.println(serializa.writeValueAsString(item));
//				}catch (JsonProcessingException e) {
//					e.printStackTrace();
//				}
//			});
//
//	}
	
	/*
	// PRUEBAS 1 - Utilizando directamente sin capas
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

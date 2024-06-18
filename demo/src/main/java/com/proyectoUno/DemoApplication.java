package com.proyectoUno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.proyectoUno.ioc.Entorno;
import com.proyectoUno.ioc.Rango;
import com.proyectoUno.ioc.Saluda;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

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
}

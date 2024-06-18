package com.proyectoUno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.proyectoUno.ioc.Entorno;
import com.proyectoUno.ioc.Saluda;
import com.proyectoUno.ioc.SaludaEnImpl;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	Saluda saludaEs;
	@Autowired
	Saluda saludaEn;
	@Autowired
	Entorno entorno;
	
//	@Autowired(required = false) //Si puede lo inyecta, y si no no protesta
//	SaludaEnImpl kk;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		var saluda = new Saluda();
		System.out.println(saludaEs.getContador());
		saludaEs.saluda("Mundo");
		saludaEn.saluda("Mundo");
		System.out.println(saludaEs.getContador());
		System.out.println(saludaEn.getContador());
		System.out.println(entorno.getContador());
	}
}

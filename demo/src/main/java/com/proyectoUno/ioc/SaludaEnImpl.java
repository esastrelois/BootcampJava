package com.proyectoUno.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaludaEnImpl implements Saluda {
	@Autowired
	Entorno entorno;
	
	public SaludaEnImpl(Entorno entorno) {
		this.entorno = entorno;
	}
	@Override
	public void saluda(String nombre) {
		entorno.write("Hello "+nombre);
	}
	
	
}

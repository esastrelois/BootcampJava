package com.proyectoUno.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class SaludaImpl implements Saluda {
	@Autowired
	Entorno entorno;
	
	public SaludaImpl(Entorno entorno) {
		this.entorno = entorno;
	}
	@Override
	public void saluda(String nombre) {
		entorno.write("Hola "+nombre);
	}
	
	
}

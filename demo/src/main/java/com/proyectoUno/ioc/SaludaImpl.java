package com.proyectoUno.ioc;

//@Component
public class SaludaImpl implements Saluda {
	Entorno entorno;
	
	public SaludaImpl(Entorno entorno) {
		this.entorno = entorno;
	}

	@Override
	public void saluda(String nombre) {
		entorno.write("Hola " + nombre);
	}

	@Override
	public int getContador() {
		return entorno.getContador();
	}
}

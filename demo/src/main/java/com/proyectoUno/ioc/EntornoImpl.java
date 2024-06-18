package com.proyectoUno.ioc;

//@Service
public class EntornoImpl implements Entorno {
	private int contador;
	
	public EntornoImpl(int contador) {
		this.contador = contador;
	}

	@Override
	public void write(String cadena) {
		contador++;
		System.out.println(cadena);
	}

	public int getContador() {
		return contador;
	}
	
}
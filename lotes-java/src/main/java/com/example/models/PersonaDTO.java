package com.example.models;

import lombok.Data;

@Data
/* 
 * Objeto con los campos que tiene el csv que vamos a leer, 
 * para transformarlo a objeto Persona y meterlo en la base de datos 
 */
public class PersonaDTO {
	private long id;
	private String nombre;
	private String apellidos;
	private String correo;
	private String sexo;
	private String ip;
}

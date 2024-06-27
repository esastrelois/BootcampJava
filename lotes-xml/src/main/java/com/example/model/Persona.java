package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/* 
 * Objeto con los campos que tiene la tabla en la base de datos 
 */
public class Persona {
	private long id;
	private String nombre;
	private String correo;
	private String ip;
}

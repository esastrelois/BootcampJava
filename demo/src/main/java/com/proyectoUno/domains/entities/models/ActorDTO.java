package com.proyectoUno.domains.entities.models;

import java.io.Serializable;

import com.proyectoUno.domains.entities.Actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Clase para la prueba de Lombok */
@Data
@AllArgsConstructor
public class ActorDTO implements Serializable{
	private int actorId;
	private String firstName;
	private String lastName;
	
	//Si tengo una entidad y necesito un DTO:
	public static ActorDTO from(Actor source) {
		return new ActorDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
	
	//Si tengo un DTO y necesito una entidad:
	public static Actor from(ActorDTO source) {
		return new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
	
}

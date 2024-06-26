package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;

class ActorTest {
	Actor actor;
	List<Actor> actores = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		actor = new Actor();
		actores = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Comparar dos actores con el mismo id")
		    public void testEquals() {
				actor.setActorId(1);
		    	Actor actor2= new Actor();
		    	actor2.setActorId(1);
		    	
		    	assertTrue(actor.equals(actor2));
			}
			@Test
			@DisplayName("FirstName no valido")
			public void testFirstNameNoValido() {
				actor.setFirstName("A");
				assertTrue(actor.isInvalid());
			}
			@Test
			@DisplayName("lastName no valido")
			public void testLastNameNoValido() {
				actor.setLastName("B");
				assertTrue(actor.isInvalid());
			}
		}
	}
}

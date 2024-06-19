package com.proyectoUno.proyectoUno_maven;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PersonaTest {

	@Nested
	@DisplayName("Proceso de instanciacion")
	class Create {
		@Nested
		class OK{
			@Test
			@DisplayName("Solo con nombre")
			void soloNombre() {
				var persona = new Persona(1, "Pepito");
				
				assertNotNull(persona);
				//Para casos de comprobaciones múltiples dentro del mismo caso, se utiliza assertAll
				assertAll("Persona", 
						() -> assertEquals(1, persona.getId(), "id"),
						() -> assertEquals("Pepito", persona.getNombre(), "nombre"),
						() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
			@Test
			@DisplayName("Con nombre y apellidos")
			void conNombreApellidos() {
				var persona = new Persona(1, "Pepito", "Grillo");
				
				assertNotNull(persona);
				assertAll("Persona", 
						() -> assertEquals(1, persona.getId(), "id"),
						() -> assertEquals("Pepito", persona.getNombre(), "nombre"),
						() -> assertTrue(persona.getApellidos().isPresent(), "apellidos"));
			}
		}
		
		@Nested
		class KO{
		}
		
	}
	


}

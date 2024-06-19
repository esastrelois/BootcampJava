package com.proyectoUno.proyectoUno_maven;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
			
			//Con la parametrizada podemos ejecutarlo a la vez en diferentes casos
			@ParameterizedTest
			@CsvSource(value = {"1,Pepito", "2,Pepito Grillo","3,'Grillo, Pepito'"}) //Para poder cogerlo con la coma, hay que usar comillas simples, ya que si no solo coge lo de antes de la coma
			void soloNombre(int id, String nombre) {
				var persona = new Persona(id, nombre);
				
				assertNotNull(persona);
				//Para casos de comprobaciones múltiples dentro del mismo caso, se utiliza assertAll
				assertAll("Persona", 
						() -> assertEquals(id, persona.getId(), "id"),
						() -> assertEquals(nombre, persona.getNombre(), "nombre"),
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
			@ParameterizedTest
			@CsvSource(value = {"4,''","3,","5,'     '"}) //Para poder cogerlo con la coma, hay que usar comillas simples, ya que si no solo coge lo de antes de la coma
			void soloNombre(int id, String nombre) {	
				assertThrows(Exception.class, () -> new Persona(id, nombre));
			}
		}
		
	}
	


}

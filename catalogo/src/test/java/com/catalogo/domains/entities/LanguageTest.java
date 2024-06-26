package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Language;

class LanguageTest {

	Language lenguaje;
	List<Actor> lenguajes = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		lenguaje = new Language();
		lenguajes = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Comparar dos lenguajes con el mismo id")
		    public void testEquals() {
				lenguaje.setLanguageId(1);
		    	Language lenguaje2= new Language();
		    	lenguaje2.setLanguageId(1);
		    	
		    	assertTrue(lenguaje.equals(lenguaje2));
			}
			@Test
			@DisplayName("Name no valido")
			public void testNameNoValido() {
				lenguaje.setName("Comparar dos lenguajes con el mismo id");
				assertTrue(lenguaje.isInvalid());
			}
		}
	}
}

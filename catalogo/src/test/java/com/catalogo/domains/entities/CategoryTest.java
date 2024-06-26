package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Category;

class CategoryTest {

	Category categoria;
	List<Category> categorias = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		categoria = new Category();
		categorias = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Comparar dos categorías con el mismo id")
		    public void testEquals() {
				categoria.setCategoryId(1);
		    	Category categoria2= new Category();
		    	categoria2.setCategoryId(1);;
		    	
		    	assertTrue(categoria.equals(categoria2));
			}
			@Test
			@DisplayName("Name no valido")
			public void testNameNoValido() {
				categoria.setName("Documentaries on Climate Change");
				assertTrue(categoria.isInvalid());
			}
		}
	}

}

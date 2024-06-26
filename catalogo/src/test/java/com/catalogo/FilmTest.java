package com.catalogo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.catalogo.domains.entities.Film;

@DisplayName("Pruebas de la clase Film")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class FilmTest {
	Film film;
	List<Film> films = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		film = new Film();
		films = new ArrayList<>();
	}
	
	@Nested
	@DisplayName("Validacion de datos")
	class validacionDatos{
		@Nested
		class OK {
			@Test
			@DisplayName("Comparar dos películas con el mismo id")
		    public void testEquals() {
		    	film.setFilmId(1);
		    	Film film2 = new Film();
		    	film2.setFilmId(1);
		    	
		    	assertTrue(film.equals(film2));
		    }
			@Test
			@DisplayName("Introducir datos no validos")
			public void testTituloNoValido() {
				film.setTitle("Exploración profunda de los efectos intergeneracionales del cambio climático global en comunidades vulnerables: un estudio longitudinal y comparativo en regiones costeras del sudeste asiático y el impacto en la seguridad alimentaria y la migración humana");
				assertTrue(film.isInvalid());
			}
		}
	}

    
    
    
    
    
    
    
    
}
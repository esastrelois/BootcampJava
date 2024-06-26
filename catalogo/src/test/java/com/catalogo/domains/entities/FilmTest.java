package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("Pruebas de la clase Film")
@TestMethodOrder(MethodOrderer.MethodName.class)
@SpringBootTest
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
		}
		@Nested
		class KO{
			@Test
			@DisplayName("Length no valida")
			public void testLengthNoValida() {
				film.setLength(2000);
				assertTrue(film.isInvalid());
			}
			@Test
			@DisplayName("Rating no válido")
			public void testRatingNoValido() {
			    film.setRating(null);
			        
				assertTrue(film.isInvalid());
			}
			@Test
			@DisplayName("Rental rate no válido")
			public void testRentalRateNoValido() {
				// Introducir un valor no válido
		        film.setRentalRate(new BigDecimal("12345.67"));
		        assertTrue(film.isInvalid());
			}
			@Test
			@DisplayName("Replacement cost no válido")
			public void testReplacementCostNoValido() {
				// Introducir un valor no válido
		        film.setReplacementCost(new BigDecimal("123456.78"));
		        assertTrue(film.isInvalid());
			}
			@Test
			@DisplayName("Titulo no valido")
			public void testTituloNoValido() {
				film.setTitle("Exploración profunda de los efectos intergeneracionales del cambio climático global en comunidades vulnerables: un estudio longitudinal y comparativo en regiones costeras del sudeste asiático y el impacto en la seguridad alimentaria y la migración humana");
				assertTrue(film.isInvalid());
			}
		}
	}
    
}
package com.proyectoUno.proyectoUno_maven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {
	Calculadora calculadora;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/* Método configurado para que:
	 Cada vez que se ejecute un test de calculadora, coja una instancia nueva y limpia de la calculadora
	 Permite quitar el "var calculadora = new Calculadora(); --> de cada uno de los test
	*/
	@BeforeEach
	void setUp() throws Exception {
		calculadora = new Calculadora(); 
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("Metodo Add")
	class Add {
		@Nested
		class OK {
			@Test
			@DisplayName("Suma dos enteros")
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition}/{totalRepetitions}") //Hace que se repita el test tantas veces como se indique en el value
			void testAdd() {	
				assertEquals(3, calculadora.add(1, 2));
			}
			
			@Test
			@DisplayName("Suma dos reales")
			void testAdd2() {
				assertEquals(0.3, calculadora.add(0.1, 0.2));
			}
		}
		
		@Nested
		class KO {
		}
	}

	@Nested
	@DisplayName("Metodo Div")
	class Div {
		@Nested
		class OK {
			@Test
			@DisplayName("Divide dos enteros")
			void testDivInt() {
				assertEquals(1, calculadora.div(3, 2));
			}
			
			@Test
			@DisplayName("Suma dos reales")
			void testDivRealOK() {	
				assertEquals(1.5, calculadora.div(3.0, 2.0));
			}
		}
		
		@Nested
		class KO {
			@Test
			@DisplayName("Division por 0")
			void testDivRealKO() {
				assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
			}
		}
	}
	
}

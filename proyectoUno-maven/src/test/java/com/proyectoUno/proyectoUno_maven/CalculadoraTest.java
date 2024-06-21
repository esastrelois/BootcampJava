package com.proyectoUno.proyectoUno_maven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Pruebas de la clase Calculadora")
@TestMethodOrder(MethodOrderer.MethodName.class)
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
			
			//Con la parametrizada podemos ejecutarlo a la vez en diferentes casos
			@ParameterizedTest(name = "Caso {index}: {0} + {1} = {2}")
			@DisplayName("Suma dos enteros")
			@CsvSource(value = {"1,2,3","3,-1,2","-1,2,1","-2,-3,-5","0,1,1","0.1,0.2,0.3"}) //value= {Operando 1, operando 2, resultado}
			void testAdd(double operando1, double operando2, double resultado) {	
				assertEquals(resultado, calculadora.add(operando1, operando2));
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
		
		/*
		En caso de querer probar algo, independientemente de otra clase, puedo simular
		que siempre salga lo que yo quiera para que no interfieran sus fallos en mi test actual.
		Para eso se utiliza --> Mockito
		*/
		@Test
		void simula() {
			Calculadora calculadora = mock(Calculadora.class); //Crea una clase que tiene lo mismo que la que simula
			when(calculadora.add(anyDouble(), anyDouble())).thenReturn(3.0).thenReturn(4.0); //Cuando te pidan esto (anyDouble()), quiero que devuelvas esto (thenReturn)
			
			assertEquals(3, calculadora.add(2.0,2.0));
			assertEquals(4, calculadora.add(12.0,2.0));
			assertEquals(4, calculadora.add(1.0,2.0));
		}
	}
	
}

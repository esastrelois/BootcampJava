# BootcampJava

## Secciones

* Demo:
  * Saluda, entorno y rango - DemoApplication Sin capas (Con pruebas 1)
    * Funcionamiento inicial de etiquetas @Service, @Component, @Scope, @Qualifier, @ConfigurationProperties
    * Evitar nulos (@Nonnull)
    * Uso de perfiles (@Profile)
  * Sakila (Actores y peliculas)- DemoApplication usando repositorios (Con pruebas 2) y usando servicios (Con pruebas 3)
    * Ejemplos CRUD y Querys con JPA, JPQL y SQL. Con BBDD Sakila
    * Carga apresurada (EAGER) vs perezosa (LAZY)
    * Validations
    * ObjetosDTO
    * Proyecciones
    * Paginación (PageRequest)
    * Serialización
    * RestController (Resource)
  * Calculator
    * Proxy y WS
    * Conexión con servidor de demo-ws
    * Suma, resta, multiplicación y división con xsd y endpoint
* ProyectoUno-maven:
  * Calculadora y persona
    * Test básicos, test parametrizados (@ParameterizedTest) y varios casos en un mismo test (@CsvSource).
    * Organización de test (@Nested, @DiplayName)
    * Creación de etiquetas para ejecutar test específicos (@Tag)
    * Ordenación de test (@TestMethodOrder)
    * Test introducción a Mockito (obligar a que devuelva lo indicado, por si se necesitase realizar otras pruebas posteriores)
  * GildedRose:
    * Testing 
    * Refactorización del código
    * Test introducción a Mockito (evitando equivocaciones por acceso a BBDD desde el DAO)
* Catalogo:
  * Sakila (una parte)
    * Implementación completa por capas, de una parte de la BBDD de Sakila. 
    * Querys con JPQL y SQL. 
    * Testing con jUnit y Mockito. 
    * Uso de objetos DTO. 
    * Excepciones y validaciones
    * Microservicios
* Lotes-java:
  * Personas
    * Importación de CSV a BBDD
    * Exportación de BBDD a CSV
    * Importación de XML a BBDD
    * Exportación de BBDD a XML
* Lotes-xml
  * Personas
    * Transformación de XML a CSV
* Demo-ws
  * Calculator
    * Endpoint
    * Lanzamiento del servidor
  
    
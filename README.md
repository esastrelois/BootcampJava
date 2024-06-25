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
  * Implementación completa por capas, de una parte de la BBDD de Sakila
package com.proyectoUno.proyectoUno_maven.Repository;

import java.util.List;
import java.util.Optional;

/* 
 Repositorio general - Los repositorios que se implementen extienden de este
 */
public interface Repository<E, K> {
	List<E> getAll();
	Optional<E> getOne(K id);
	E add(E item);
	E modify(E item);
	void delete(E item);
	void deleteById(K id);
}
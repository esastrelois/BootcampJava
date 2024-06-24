package com.proyectoUno.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectoUno.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>{
	
}

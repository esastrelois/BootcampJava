package com.catalogo.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.catalogo.domains.core.contrats.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>,
RepositoryWithProjections{
}

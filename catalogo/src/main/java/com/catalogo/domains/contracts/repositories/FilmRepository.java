package com.catalogo.domains.contracts.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.catalogo.domains.core.contrats.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>,
RepositoryWithProjections{
	// Encuentra películas por título (case insensitive)
    List<Film> findByTitleIgnoreCase(String title);

    // Encuentra películas por año de lanzamiento
    List<Film> findByReleaseYear(Short releaseYear);

    // Encuentra películas por calificación (rating)
    List<Film> findByRating(String rating);

    // Encuentra películas con una duración de alquiler específica
    List<Film> findByRentalDuration(byte rentalDuration);

    // Encuentra películas por una tasa de alquiler mayor o igual a un valor específico
    List<Film> findByRentalRateGreaterThanEqual(BigDecimal rentalRate);
}

package com.catalogo.domains.entities.models;

import java.io.Serializable;

import com.catalogo.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDTO implements Serializable {
	@JsonProperty("id")
	private int filmId;
	@JsonProperty("descripcion")
	private String description;
	@JsonProperty("fecha")
	private Short releaseYear;
	
	public static FilmDTO from(Film source) {
		return new FilmDTO(
				source.getFilmId(),
				source.getDescription(),
				source.getReleaseYear()
				);
	}
	
	public static Film from(FilmDTO source) {
		return new Film(
				source.getFilmId(),
				source.getDescription(),
				source.getReleaseYear()
				);
	}
}
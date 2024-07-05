package com.catalogo.application.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.catalogo.domains.entities.models.FilmShortDTO;

@FeignClient(name = "CATALOGO-SERVICE" /*, url = "http://localhost:8010"*/)
public interface CatalogoProxy {
	@GetMapping(path = "/")
	String getCatalogo();
	
	@GetMapping(path = "/actuator/info")
	String getInfo();
	
	@GetMapping(path = "/peliculas/v1?mode=short")
	List<FilmShortDTO> getPelis();
	@GetMapping(path = "/peliculas/v1?mode=details")
	List<FilmShortDTO> getPelisConDetalle();
	
	@GetMapping(path = "/peliculas/v1/{id}?mode=short")
	FilmShortDTO getPeli(@PathVariable int id);
	
	@PostMapping(path = "/peliculas/v1/{id}/like")
	String meGusta(@PathVariable int id);
	@PostMapping(path = "/peliculas/v1/{id}/like")
	String meGusta(@PathVariable int id, @RequestHeader(value = "Authorization", required = true) String authorization);
}
package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contrats.services.ActorService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Tag(name = "actores-service", description = "Mantenimiento de actores")
@RequestMapping("/api/actores/v1")
public class ActorResource {
	private ActorService srv;

	public ActorResource(ActorService srv) {
		this.srv = srv;
	}
	
	@Operation(summary = "Listado de todos los actores")
	@ApiResponse(responseCode = "200", description = "OK")
	@GetMapping
	public List getAll(
			 @Parameter(description = "Modo de vista de los actores", required = false, schema = @Schema(allowableValues = {"short", "largo"}, defaultValue = "largo")) 
			@RequestParam(required = false, defaultValue = "largo") String modo) {
		if("short".equals(modo))
			return srv.getByProjection(ActorShort.class);
		else
			return srv.getByProjection(ActorDTO.class);
	}
	
	/* 
	 * Muestra por defecto 20 elementos en cada página
	 */
	@Hidden
	@GetMapping(params = "page")
	public Page<ActorShort> getAll(
			@Parameter(hidden = true) Pageable page){
		return srv.getByProjection(page, ActorShort.class);
	}
	
	@Operation(summary = "Recupera un actor", description = "Está disponible en versión corta con los datos imprescindibles del actor.")
	@ApiResponse(responseCode = "200", description = "Actor encontrado", content = @Content(schema = @Schema(oneOf = {
			ActorDTO.class })))
	@ApiResponse(responseCode = "404", description = "Actor no encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
	@GetMapping(path = "/{id}")
	public ActorDTO getOne(
			@Parameter(description = "Identificador del actor", required = true) 
			@PathVariable int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}
	
	//Un record se utiliza para crear una clase inmutable --> usar cuando solo se va a usar una vez
	record Peli(int id, String titulo) {}
	
	@Operation(summary = "Listado de las peliculas del actor")
	@ApiResponse(responseCode = "200", description = "Actor encontrado")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<Peli> getPelis(
			@Parameter(description = "Identificador del actor", required = true) 
			@PathVariable int id) throws NotFoundException{
		var item = srv.getOne(id);
		if(item.isEmpty())
			throw new NotFoundException();
		return item.get().getFilmActors().stream()
				.map(o -> new Peli(o.getFilm().getFilmId(),o.getFilm().getTitle()))
				.toList();
	}
	
	@Operation(summary = "Añadir un nuevo actor")
	@ApiResponse(responseCode = "201", description = "Actor añadido")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> create(@Valid @RequestBody Actor item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();	
	}
	
	@Operation(summary = "Añadir un nuevo actor - version corta")
	@ApiResponse(responseCode = "201", description = "Actor añadido")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@PostMapping("/corto")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> createCorto(@Valid @RequestBody ActorDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();	
	}
	
	@Operation(summary = "Modificar un actor existente", description = "Los identificadores deben coincidir")
	@ApiResponse(responseCode = "200", description = "Actor encontrado")
	@ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(
			@Parameter(description = "Identificador del actor", required = true) 
			@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException,  InvalidDataException {
		if(id != item.getActorId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(ActorDTO.from(item));
	}
	
	@Operation(summary = "Borrar un actor existente")
	@ApiResponse(responseCode = "204", description = "Actor borrado")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(
			@Parameter(description = "Identificador del actor", required = true) 
			@PathVariable int id) throws BadRequestException, NotFoundException,  InvalidDataException {
		if(srv.getOne(id).isEmpty())
    		throw new NotFoundException();
		srv.deleteById(id);
	}
	
}

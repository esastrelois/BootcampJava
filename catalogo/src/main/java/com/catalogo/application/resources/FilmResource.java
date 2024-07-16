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

import com.catalogo.domains.contrats.services.FilmService;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.FilmEditDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@Tag(name = "peliculas-service", description = "Mantenimiento de peliculas")
@RequestMapping("/api/peliculas/v1")
public class FilmResource {
    private FilmService srv;
    
    public FilmResource(FilmService srv) {
        this.srv = srv;
    }

    @Operation(summary = "Listado de todas las peliculas")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List getAll(
            @Parameter(description = "Modo de vista de las peliculas", required = false, schema = @Schema(allowableValues = {"short", "largo"}, defaultValue = "largo")) 
            @RequestParam(required = false, defaultValue = "largo") String modo) {
        if ("short".equals(modo))
            return srv.getByProjection(FilmShortDTO.class);
        else
            return srv.getByProjection(Film.class);
    }

    @Operation(summary = "Listado paginado de peliculas en formato corto")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(params = "page")
    @ResponseStatus(HttpStatus.OK)
    public Page<FilmShortDTO> getAll(Pageable page) {
        return srv.getByProjection(page, FilmShortDTO.class);
    }

    @Operation(summary = "Recupera una pelicula")
    @ApiResponse(responseCode = "200", description = "Pelicula encontrada", content = @Content(schema = @Schema(implementation = FilmShortDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmShortDTO getOne(
            @Parameter(description = "Identificador de la pelicula", required = true) 
            @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return FilmShortDTO.from(item.get());
    }

    @Operation(summary = "Recupera una pelicula - versión corta")
    @ApiResponse(responseCode = "200", description = "Pelicula encontrada", content = @Content(schema = @Schema(implementation = FilmEditDTO.class)))
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}/corto")
    @ResponseStatus(HttpStatus.OK)
    public FilmEditDTO getOneCorto(
            @Parameter(description = "Identificador de la pelicula", required = true) 
            @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return FilmEditDTO.from(item.get());
    }

    record Act(int id, String firstName, String lastName) {}

    @Operation(summary = "Listado de actores en una pelicula")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}/pelis")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<Act> get(
            @Parameter(description = "Identificador de la pelicula", required = true) 
            @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return item.get().getFilmActors().stream()
                .map(o -> new Act(o.getActor().getActorId(), o.getActor().getFirstName(), o.getActor().getLastName()))
                .toList();
    }

    @Operation(summary = "Añadir una nueva pelicula")
    @ApiResponse(responseCode = "201", description = "Pelicula añadida")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos de la nueva pelicula", required = true) 
            @Valid @RequestBody Film item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getFilmId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Añadir una nueva pelicula - versión corta")
    @ApiResponse(responseCode = "201", description = "Pelicula añadida")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PostMapping("/corto")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> createCorto(
            @Parameter(description = "Datos de la nueva pelicula", required = true) 
            @Valid @RequestBody FilmEditDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        var newItem = srv.add(FilmEditDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getFilmId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modificar una pelicula existente", description = "Los identificadores deben coincidir")
    @ApiResponse(responseCode = "200", description = "Pelicula modificada", content = @Content(schema = @Schema(implementation = FilmEditDTO.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(
            @Parameter(description = "Identificador de la pelicula", required = true) 
            @PathVariable int id,
            @Parameter(description = "Datos de la pelicula a modificar", required = true) 
            @Valid @RequestBody FilmEditDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        if (id != item.getFilmId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(FilmEditDTO.from(item));
    }

    @Operation(summary = "Borrar una pelicula existente")
    @ApiResponse(responseCode = "204", description = "Pelicula borrada")
    @ApiResponse(responseCode = "404", description = "Pelicula no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
    		@Parameter(description = "Identificador de la pelicula", required = true) 
    		@PathVariable int id) throws BadRequestException, NotFoundException, InvalidDataException {
    	if(srv.getOne(id).isEmpty())
    		throw new NotFoundException();
        srv.deleteById(id);
    }
 }
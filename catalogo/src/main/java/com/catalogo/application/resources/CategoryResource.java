package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contrats.services.CategoryService;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;

@RestController
@Tag(name = "categorias-service", description = "Mantenimiento de categorias")
@RequestMapping(path = "/api/categorias/v1")
public class CategoryResource {
    @Autowired
    CategoryService srv;

    @Operation(summary = "Listado de todas las categorias")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAll() {
        return srv.getAll();
    }

    @Operation(summary = "Recupera una categoria", description = "Recupera los detalles de una categoria por su ID")
    @ApiResponse(responseCode = "200", description = "Categoria encontrada", content = @Content(schema = @Schema(implementation = Category.class)))
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getOne(
            @Parameter(description = "Identificador de la categoria", required = true) @PathVariable int id) throws NotFoundException {
        var category = srv.getOne(id);
        if (category.isEmpty())
            throw new NotFoundException();
        else
            return category.get();
    }

    @Operation(summary = "Listado de peliculas en una categoria")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}/peliculas")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public List<FilmShortDTO> getPelis(
            @Parameter(description = "Identificador de la categoria", required = true) @PathVariable int id) throws NotFoundException {
        var category = srv.getOne(id);
        if (category.isEmpty())
            throw new NotFoundException();
        else {
            return category.get().getFilmCategories().stream()
                    .map(item -> FilmShortDTO.from(item.getFilm()))
                    .collect(Collectors.toList());
        }
    }

    @Operation(summary = "Añadir una nueva categoria")
    @ApiResponse(responseCode = "201", description = "Categoria añadida")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    @Transactional
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos de la nueva categoria", required = true) @Valid @RequestBody Category item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        var newItem = srv.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getCategoryId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modificar una categoria existente", description = "Los identificadores deben coincidir")
    @ApiResponse(responseCode = "200", description = "Categoria modificada", content = @Content(schema = @Schema(implementation = Category.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PutMapping("/{id}")
    public Category update(
            @Parameter(description = "Identificador de la categoria", required = true) @PathVariable int id,
            @Parameter(description = "Datos de la categoria a modificar", required = true) @Valid @RequestBody Category item) throws BadRequestException, NotFoundException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        if (id != item.getCategoryId())
            throw new BadRequestException("No coinciden los identificadores");
        return srv.modify(item);
    }

    @Operation(summary = "Borrar una categoria existente")
    @ApiResponse(responseCode = "204", description = "Categoria borrada")
    @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Identificador de la categoria", required = true) 
            @PathVariable int id) throws BadRequestException, NotFoundException,  InvalidDataException {
    	if(srv.getOne(id).isEmpty())
    		throw new NotFoundException();
        srv.deleteById(id);
    }
}
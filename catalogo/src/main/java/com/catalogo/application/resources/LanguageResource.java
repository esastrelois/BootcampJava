package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;

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

import com.catalogo.domains.contrats.services.LanguageService;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.LanguageDTO;
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
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;

@RestController
@Tag(name = "lenguajes-service", description = "Mantenimiento de lenguajes")
@RequestMapping("/api/lenguajes/v1")
public class LanguageResource {
    @Autowired
    LanguageService srv;

    @Operation(summary = "Listado de todos los lenguajes")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<Language> getAll() {
        return srv.getAll();
    }

    @Operation(summary = "Recupera un lenguaje", description = "Recupera los detalles de un lenguaje por su ID")
    @ApiResponse(responseCode = "200", description = "Lenguaje encontrado", content = @Content(schema = @Schema(implementation = Language.class)))
    @ApiResponse(responseCode = "404", description = "Lenguaje no encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}")
    public Language getOne(
            @Parameter(description = "Identificador del lenguaje", required = true) @PathVariable int id) throws NotFoundException {
        var language = srv.getOne(id);
        if (language.isEmpty())
            throw new NotFoundException();
        else
            return language.get();
    }

    @Operation(summary = "Recupera un lenguaje - versión corta")
    @ApiResponse(responseCode = "200", description = "Lenguaje encontrado", content = @Content(schema = @Schema(implementation = LanguageDTO.class)))
    @ApiResponse(responseCode = "404", description = "Lenguaje no encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping(path = "/{id}/V2")
    public LanguageDTO getOneCorto(
            @Parameter(description = "Identificador del lenguaje", required = true) @PathVariable int id) throws NotFoundException {
        var item = srv.getOne(id);
        if (item.isEmpty())
            throw new NotFoundException();
        return LanguageDTO.from(item.get());
    }

    @Operation(summary = "Añadir un nuevo lenguaje")
    @ApiResponse(responseCode = "201", description = "Lenguaje añadido")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> create(
            @Parameter(description = "Datos del nuevo lenguaje", required = true) @Valid @RequestBody Language item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        var newItem = srv.add(item);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getLanguageId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Añadir un nuevo lenguaje - versión corta")
    @ApiResponse(responseCode = "201", description = "Lenguaje añadido")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PostMapping("/corto")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Object> createCorto(
            @Parameter(description = "Datos del nuevo lenguaje", required = true) @Valid @RequestBody LanguageDTO item) throws BadRequestException, DuplicateKeyException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        var newItem = srv.add(LanguageDTO.from(item));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newItem.getLanguageId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modificar un lenguaje existente", description = "Los identificadores deben coincidir")
    @ApiResponse(responseCode = "200", description = "Lenguaje modificado", content = @Content(schema = @Schema(implementation = Language.class)))
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "404", description = "Lenguaje no encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @PutMapping("/{id}")
    public void update(
            @Parameter(description = "Identificador del lenguaje", required = true) @PathVariable int id,
            @Parameter(description = "Datos del lenguaje a modificar", required = true) @Valid @RequestBody LanguageDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
        if (item == null)
            throw new BadRequestException("Faltan los datos");
        if (id != item.getLanguageId())
            throw new BadRequestException("No coinciden los identificadores");
        srv.modify(LanguageDTO.from(item));
    }

    @Operation(summary = "Borrar un lenguaje existente")
    @ApiResponse(responseCode = "204", description = "Lenguaje borrado")
    @ApiResponse(responseCode = "404", description = "Lenguaje no encontrado", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "Identificador del lenguaje", required = true) 
            @PathVariable int id) throws BadRequestException, NotFoundException,  InvalidDataException {
    	if(srv.getOne(id).isEmpty())
    		throw new NotFoundException();
        srv.deleteById(id);
    }
}
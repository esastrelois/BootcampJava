package com.proyectoUno.application.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.proyectoUno.domains.entities.models.FilmShortDTO;

@FeignClient(name="photos", url="https://picsum.photos")
public interface FilmProxy {
    @GetMapping(value = "/v2/list?limit=1000", consumes = {"application/json"})
    List<FilmShortDTO> getAll();
    @GetMapping("/id/{id}/info")
    FilmShortDTO getOne(@PathVariable int id);
    
    @PostMapping(path = "/photos", consumes = { "application/json"} )
    void send(@RequestBody FilmShortDTO item);
}
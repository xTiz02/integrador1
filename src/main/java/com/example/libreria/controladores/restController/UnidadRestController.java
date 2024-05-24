package com.example.libreria.controladores.restController;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroUnidad;
import com.example.libreria.services.daos.LibroUnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/libroUnidad")
public class UnidadRestController {
    @Autowired
    private LibroUnidadService libroUnidadService;

    @GetMapping(value = "/listar/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LibroUnidad> listar(@PathVariable(name = "isbn") String isbn){
        return libroUnidadService.traerLibrosUnidadPorIsbn(isbn).stream().filter(libroUnidad -> libroUnidad.getEstado().equals(Util.ESTADO_DISPONIBLE)).toList();
    }
}

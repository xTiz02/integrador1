package com.example.libreria.controladores.restController;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroUnidad;
import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.services.daos.LibroService;
import com.example.libreria.services.daos.LibroUnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/libro")
public class LibroRestController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private LibroUnidadService libroUnidadService;

    @GetMapping(value = "/listar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Libro> listar(@PathVariable(name = "id") Long id){
        return libroService.traerLibrosPorCategoriaYEstado(id, Util.ESTADO_DISPONIBLE);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Libro buscar(@PathVariable(name = "id") String id){
        return libroService.traerLibroPorId(Long.parseLong(id));
    }

    @GetMapping(value = "/listarUnidades/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<LibroUnidad> listarUnidades(@PathVariable(name = "isbn") String isbn){
        return libroUnidadService.traerLibrosUnidadPorIsbn(isbn);
    }
}

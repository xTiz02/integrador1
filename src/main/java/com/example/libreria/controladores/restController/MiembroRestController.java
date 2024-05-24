package com.example.libreria.controladores.restController;

import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.services.daos.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/miembro")
public class MiembroRestController {
    @Autowired
    private MiembroService miembroService;

    @GetMapping(value = "/listar/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Miembro> listar(@PathVariable(name = "tipo") String tipo){
        System.out.println("tipo: " + tipo);
        return miembroService.traerMiembrosPorTipo(tipo);
    }
    @GetMapping(value = "/buscar/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Miembro buscar(@PathVariable(name = "id") Long id){
        return miembroService.traerMiembroPorId(id);
    }

}

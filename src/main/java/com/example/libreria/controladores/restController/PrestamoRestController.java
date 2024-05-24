package com.example.libreria.controladores.restController;

import com.example.libreria.modelo.entidades.*;
import com.example.libreria.modelo.entidades.dto.LibroPrestadoDto;
import com.example.libreria.modelo.entidades.dto.PrestamoDto;
import com.example.libreria.modelo.entidades.dto.ViewPrestamoDto;
import com.example.libreria.services.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.MapUtils;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping(value = "/rest/prestamo")
public class PrestamoRestController {

    @Autowired
    private PrestamoService prestamoService;



    @RequestMapping(value = "/guardar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String guardar(@RequestBody PrestamoDto data) throws ParseException {
        prestamoService.guardarPrestamo(data);
        return "completado";
    }




    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String returnSelected(@RequestParam Map<String, String> payload, @PathVariable(name = "id") Long id) {

        Prestamo prestamo = prestamoService.traerPrestamoPorId(id);
        System.out.println("Préstamo encontrado: "+prestamo.getId());
        if(!MapUtils.isEmpty(payload)){
            String[] librosPrestadosIds = payload.get("ids").contains(",") ? payload.get("ids").split(",") : new String[]{payload.get("ids")};
            for (String s : librosPrestadosIds) {
                System.out.println("LibroPrestado: " + s);
            }
            prestamo = prestamoService.retornarPrestamo(id, librosPrestadosIds);
        }else{
            prestamo = null;
        }

        return prestamo!=null ? "completado" : "error";
    }

    @GetMapping(value = "/ver/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ViewPrestamoDto ver(@PathVariable(name = "id") Long id){
        Prestamo prestamo = prestamoService.traerPrestamoPorId(id);
        return ViewPrestamoDto.toPrestamoDto(prestamo);
    }
}

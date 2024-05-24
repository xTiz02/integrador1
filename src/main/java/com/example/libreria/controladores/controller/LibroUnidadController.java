package com.example.libreria.controladores.controller;


import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroUnidad;
import com.example.libreria.services.daos.LibroUnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/libroUnidad")
@SessionAttributes("libroUnidad")
public class LibroUnidadController {

    @Autowired
    private LibroUnidadService libroUnidadService;

    @RequestMapping(value = "/nuevo/{nombre}", method = RequestMethod.GET)
    public String crear( Model modelo, @PathVariable("nombre") String nombre){
        LibroUnidad libroUnidad = (LibroUnidad) modelo.asMap().get("libroUnidad");
        if(libroUnidad !=null){
            modelo.addAttribute("libroUnidad", libroUnidad);
        }
        modelo.addAttribute("titulo", "Nueva Unidad: "+nombre);
        return "unidad/form";
    }
    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardar(@Valid LibroUnidad libro, BindingResult result, SessionStatus status, RedirectAttributes flash, Model modelo){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Nueva Unidad: " + libro.getLibro().getTitulo());
            return "/unidad/form";
        }

        if(libro.getId() != null && libro.getId() > 0){
            LibroUnidad libroEdit= libroUnidadService.guardarLibroUnidad(libro);
            flash.addFlashAttribute("completado", "Unidad editada con éxito");
            status.setComplete();
            return "redirect:/libroUnidad/editar/"+libroEdit.getId();
        }else {
            libroUnidadService.guardarLibroUnidad(libro);
            status.setComplete();
            flash.addFlashAttribute("completado", "Unidad creada con éxito");
            flash.addFlashAttribute("libroUnidad", libro);

            return "redirect:/libroUnidad/nuevo/"+libro.getLibro().getTitulo();
        }
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable("id")Long id, Model modelo, RedirectAttributes flash){
        LibroUnidad libroUnidad = libroUnidadService.traerLibroUnidad(id);
        if (libroUnidad == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "La unidad no existe en la base de datos");
            return "redirect:/libroUnidad/listar";
        }
        modelo.addAttribute("libroUnidad", libroUnidad);
        modelo.addAttribute("titulo", "Editar Unidad de : "+libroUnidad.getLibro().getTitulo());
        return "unidad/form";
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String deshabilitar(@PathVariable("id")Long id, RedirectAttributes flash){
        LibroUnidad unidad = libroUnidadService.traerLibroUnidad(id);
        if (unidad == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El libro no existe en la base de datos");
            return "redirect:/libro/listar";
        }
        if(unidad.getEstado().equals(Util.ESTADO_PRESTAMO)){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "La unidad #"+ unidad.getId()+ " del libro "+unidad.getLibro().getTitulo()+" no se puede eliminar porque esta en préstamo");
            return "redirect:/libro/listar";
        }

        libroUnidadService.eliminarLibroUnidad(id);

        return "redirect:/libro/listar";
    }
}

package com.example.libreria.controladores.controller;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroUnidad;
import com.example.libreria.modelo.entidades.Usuario;
import com.example.libreria.services.daos.CategoriaService;
import com.example.libreria.services.daos.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/libro")
@SessionAttributes("libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoriaService categoriaService;


    @RequestMapping(value = {"/","/listar"}, method = RequestMethod.GET)
    public String listar(Model modelo,RedirectAttributes flash){
        modelo.addAttribute("libros", libroService.traerLibros());
        return "libro/lista";
    }

    @RequestMapping(value = "/nuevo", method = RequestMethod.GET)
    public String crear(Model modelo){
        modelo.addAttribute("listaCategorias", categoriaService.traerCategoriasSinListaDeLibros());
        modelo.addAttribute("libro", new Libro());
        modelo.addAttribute("titulo", "Nuevo Libro");
        return "libro/form";
    }
    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardar(@Valid Libro libro, BindingResult result, SessionStatus status, RedirectAttributes flash, Model modelo){
        if(result.hasErrors() || libro.getCategorias().isEmpty()){
            modelo.addAttribute("listaCategorias", categoriaService.traerCategoriasSinListaDeLibros());
            modelo.addAttribute("titulo", "Nueva Categoría");
            return "/libro/form";
        }



        if(libro.getId() != null && libro.getId() > 0){
            if(libro.getStockTotal()< (libro.getStockDisponible()+libro.getStockPrestado())){
                modelo.addAttribute("listaCategorias", categoriaService.traerCategoriasSinListaDeLibros());
                modelo.addAttribute("titulo", "Nueva Categoría");
                modelo.addAttribute("error", "El stock total no puede ser menor al stock disponible y stock prestado");
                return "/libro/form";
            }

            Libro libroEdit= libroService.guardarLibro(libro);
            flash.addFlashAttribute("completado", "Libro editado con éxito");
            status.setComplete();
            return "redirect:/libro/editar/"+libroEdit.getId();
        }else {

            libroService.guardarLibro(libro);
            flash.addFlashAttribute("completado", "Libro creado con éxito");
            status.setComplete();
            return "redirect:/libro/nuevo";
        }
    }

    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable("id")Long id, Model modelo, RedirectAttributes flash){
        Libro libro = libroService.traerLibroPorId(id);
        if (libro == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El libro no existe en la base de datos");
            return "redirect:/libro/listar";
        }
        modelo.addAttribute("listaCategorias", categoriaService.traerCategoriasSinListaDeLibros());
        modelo.addAttribute("libro", libro);
        modelo.addAttribute("titulo", "Editar Libro");
        return "libro/form";
    }

    @RequestMapping(value = "/nuevaUnidad/{idLibro}", method = RequestMethod.GET)
    public String nuevaUnidad(@PathVariable("idLibro")Long idLibro, Model modelo, RedirectAttributes flash){
        Libro libro = libroService.traerLibroPorId(idLibro);
        if (libro == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El libro no existe en la base de datos");
            return "redirect:/libro/listar";
        }
        LibroUnidad libroUnidad = new LibroUnidad();
        libroUnidad.setLibro(libro);
        libroUnidad.setIsbn(libro.getIsbn());
        flash.addFlashAttribute("libroUnidad", libroUnidad);
        return "redirect:/libroUnidad/nuevo/"+libro.getTitulo();
    }

    @RequestMapping(value = "/cambiar/{id}", method = RequestMethod.GET)
    public String deshabilitar(@PathVariable("id")Long id, RedirectAttributes flash){
        Libro libro = libroService.traerLibroPorId(id);
        if (libro == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El libro no existe en la base de datos");
            return "redirect:/libro/listar";
        }
        if(libro.getEstado().equals(Util.ESTADO_NO_DISPONIBLE)){
            libroService.cambiarEstadoLibro(id,Util.ESTADO_DISPONIBLE);
        }
        if(libro.getEstado().equals(Util.ESTADO_DISPONIBLE)){
            libroService.cambiarEstadoLibro(id,Util.ESTADO_NO_DISPONIBLE);
        }
        return "redirect:/libro/listar";
    }


}

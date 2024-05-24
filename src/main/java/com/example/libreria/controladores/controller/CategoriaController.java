package com.example.libreria.controladores.controller;


import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.services.daos.CategoriaService;
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
@SessionAttributes("categoria")
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaDao;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/","/listar"}, method = RequestMethod.GET)
    public String listar(Model modelo){
        modelo.addAttribute("categorias", categoriaDao.traerCategoriasSinListaDeLibros());
        return "/categoria/lista";
    }

    @RequestMapping(value = "/nuevo", method = RequestMethod.GET)
    public String crear(Model modelo){

        modelo.addAttribute("categoria", new Categoria());
        modelo.addAttribute("titulo", "Nueva Categoría");
        return "/categoria/form";
    }
    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String guardar(@Valid Categoria categoria, BindingResult result, SessionStatus status, RedirectAttributes flash, Model modelo){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Nueva Categoría");
            return "/categoria/form";
        }
        if(categoria.getId() != null && categoria.getId() > 0){
            String mensajeFlash = "Categoría editada con éxito";
            Categoria catEdit= categoriaDao.guardarCategoria(categoria);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/categoria/editar/"+catEdit.getId();
        }else {
            String mensajeFlash = "Categoría creada con éxito";
            categoria.setFechaCreado(new Date());
            categoriaDao.guardarCategoria(categoria);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/categoria/nuevo";
        }
    }
    @RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
    public String editar(@PathVariable(value = "id") Long id, Model modelo,RedirectAttributes flash){
        Categoria categoria = categoriaDao.traerCategoriaPorId(id);
        if (categoria == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "La categoría no existe en la base de datos");
            return "redirect:/categoria/listar";
        }

        modelo.addAttribute("categoria", categoria);
        modelo.addAttribute("titulo", "Editar Categoría");
        return "/categoria/form";
    }

    @RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        Categoria categoria = categoriaDao.traerCategoriaPorId(id);
        if (categoria == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "La categoría no existe en la base de datos");
            return "redirect:/categoria/listar";
        }
        if(categoriaDao.estaUsado(categoria)){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "La categoría no se puede eliminar porque está en uso");
            return "redirect:/categoria/listar";
        }
        if(id!=null && id > 0){
            categoriaDao.eliminarCategoria(id);
        }
        return "redirect:/categoria/listar";
    }
}

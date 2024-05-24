package com.example.libreria.controladores.controller;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.services.daos.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
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
@SessionAttributes("miembro")
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @RequestMapping("/listar")
    public String listar(Model modelo){
        modelo.addAttribute("miembros", miembroService.traerMiembros());
        return "miembro/lista";
    }
    @RequestMapping("/nuevo")
    public String crear(Model modelo){
        modelo.addAttribute("miembro", new Miembro());
        modelo.addAttribute("titulo", "Nuevo Miembro");
        modelo.addAttribute("generos", Util.TIPOS_GENEROS);
        modelo.addAttribute("tipos", Util.TIPOS_MIEMBROS);
        return "miembro/form";
    }

    @RequestMapping(value = "/guardar", method = RequestMethod.POST)
    public String agregar(@Valid Miembro miembro, BindingResult result, RedirectAttributes flash, SessionStatus status, Model modelo){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Nuevo Miembro");
            modelo.addAttribute("generos", Util.TIPOS_GENEROS);
            modelo.addAttribute("tipos", Util.TIPOS_MIEMBROS);
            return "miembro/form";
        }
        if(miembro.getId() != null && miembro.getId() > 0){
            String mensajeFlash = "Miembro editado con éxito";
            Miembro miembroEdit= miembroService.guardarMiembro(miembro);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/miembro/editar/"+miembroEdit.getId();
        }else {
            String mensajeFlash = "Miembro creado con éxito";
            miembro.setFechaIngreso(new Date());
            miembroService.guardarMiembro(miembro);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/miembro/nuevo";
        }
    }

    @RequestMapping("/editar/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model modelo,RedirectAttributes flash){
        Miembro miembro = miembroService.traerMiembroPorId(id);
        if (miembro == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El miembro no existe en la base de datos");
            return "redirect:/miembro/listar";
        }
        modelo.addAttribute("titulo", "Editar Miembro");
        modelo.addAttribute("miembro", miembro);
        modelo.addAttribute("generos", Util.TIPOS_GENEROS);
        modelo.addAttribute("tipos", Util.TIPOS_MIEMBROS);
        return "miembro/form";
    }
    @RequestMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id,RedirectAttributes flash){
        Miembro miembro = miembroService.traerMiembroPorId(id);
        if (miembro == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El miembro no existe en la base de datos");
            return "redirect:/miembro/listar";
        }
        if(miembroService.estaUsado(miembro)){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El miembro no se puede eliminar porque está en uso");
            return "redirect:/miembro/listar";
        }
        if(id!=null && id > 0){
            miembroService.eliminarMiembro(id);
        }
        return "redirect:/miembro/listar";
    }

}

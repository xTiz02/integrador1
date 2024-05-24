package com.example.libreria.controladores.controller;

import com.example.libreria.config.Util;
import com.example.libreria.services.daos.CategoriaService;
import com.example.libreria.services.daos.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;
    @Autowired
    private CategoriaService categoriaService;

    @RequestMapping("/nuevo")
    public String crear(Model modelo){
        modelo.addAttribute("tiposMiembro", Util.TIPOS_MIEMBROS);
        modelo.addAttribute("categorias", categoriaService.traerCategoriasSinListaDeLibros());
        return "prestamo/form";
    }
    @RequestMapping(value = { "/listar/{tipo}"}, method = RequestMethod.GET)
    public String listIssuePage(@PathVariable(value = "tipo") String tipo, Model model) {
        if (tipo.equals("retornados")) {
            model.addAttribute("tipoLista", "Retornados");
            model.addAttribute("prestamos", prestamoService.traerPrestamos());
        }
        if (tipo.equals("enCurso")) {
            model.addAttribute("tipoLista", "EnCurso");
            model.addAttribute("prestamos", prestamoService.traerPrestamosNoRetornados(Util.ESTADO_NO_DISPONIBLE));
        }
        return "prestamo/lista";
    }

}

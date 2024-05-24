package com.example.libreria.controladores.controller;

import com.example.libreria.services.impl.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;
    @RequestMapping (value = {"/","/home"}, method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("topParametros", homeService.getMapParametros());
        return "home";
    }
}

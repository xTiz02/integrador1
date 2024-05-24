package com.example.libreria.controladores.controller;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.Usuario;
import com.example.libreria.services.daos.RolService;
import com.example.libreria.services.impl.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.Locale;
@Controller
@SessionAttributes("usuario")
public class LoginController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JpaUserDetailService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error,
                        @RequestParam(value="logout", required = false) String logout,
                        Model model, Principal principal, RedirectAttributes flash, Locale locale) {

        if(principal != null) {
            //diferencia entre flash y model es que el flash se va a la siguiente peticion y el model no

            flash.addFlashAttribute("info", messageSource.getMessage("text.login.already", null, locale));
            return "redirect:/";//te envia a la raiz
        }

        if(error != null) {
            model.addAttribute("error", messageSource.getMessage("text.login.error", null, locale));
        }

        if(logout != null) {
            model.addAttribute("success", messageSource.getMessage("text.login.logout", null, locale));
        }

        return "login";
    }

    @RequestMapping(value = "/usuario/listar", method = RequestMethod.GET)
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuario/lista";
    }
    @RequestMapping(value = "/usuario/nuevo", method = RequestMethod.GET)
    public String nuevo(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaRoles", rolService.listarRoles());
        return "usuario/form";
    }
    @RequestMapping(value = "/usuario/guardar", method = RequestMethod.POST)
    public String crear(@Valid Usuario usuario, BindingResult result, SessionStatus status, RedirectAttributes flash, Model modelo) {
        if(result.hasErrors() || usuario.getRoles().isEmpty()){
            System.out.println("error usuario");
            modelo.addAttribute("titulo", "Nuevo Usuario");
            modelo.addAttribute("listaRoles", rolService.listarRoles());
            return "/usuario/form";
        }


        if(usuario.getId() != null && usuario.getId() > 0){
            System.out.println("editar usuario");
            String mensajeFlash = "Usuario editado con éxito";
            Usuario usuarioEdit= usuarioService.guardarUsuario(usuario);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/usuario/editar/"+usuarioEdit.getId();
        }else {
            System.out.println("guardar usuario");
            String mensajeFlash = "Usuario creado con éxito";
            usuario.setFechaCreado(new Date());
            usuario.setEnabled(true);
            usuarioService.guardarUsuario(usuario);
            flash.addFlashAttribute("completado", mensajeFlash);
            status.setComplete();
            return "redirect:/usuario/nuevo";
        }
    }


    @RequestMapping(value = "/usuario/eliminar/{id}", method = RequestMethod.GET)
    public String eliminar(@PathVariable("id")Long id, RedirectAttributes flash){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El usuario no existe en la base de datos");
            return "redirect:/usuario/listar";
        }

        if(id!=null && id > 0){
            usuarioService.eliminarUsuarioPorId(id);
        }
        return "redirect:/usuario/listar";
    }

    @RequestMapping(value = "/usuario/cambiar/{id}", method = RequestMethod.GET)
    public String cambiar(@PathVariable("id")Long id, RedirectAttributes flash){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null){
            flash.addFlashAttribute("error", true);
            flash.addFlashAttribute("mensaje", "El usuario no existe en la base de datos");
            return "redirect:/usuario/listar";
        }

        if(id!=null && id > 0){
            usuario.setEnabled(!usuario.getEnabled());
            usuarioService.guardarUsuario(usuario);
        }
        return "redirect:/usuario/listar";
    }
}

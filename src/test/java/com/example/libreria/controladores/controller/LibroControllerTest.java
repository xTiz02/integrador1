package com.example.libreria.controladores.controller;

import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.services.daos.CategoriaService;
import com.example.libreria.services.daos.LibroService;
import com.example.libreria.services.impl.MiembroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration//levanta el servidor completo pero en version de test
class LibroControllerTest {

    MockMvc mockMvc;
    @InjectMocks
    private LibroController libroController;
    @Mock
    private LibroService libroService;

    @MockBean
    private MiembroServiceImpl miembroService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private BindingResult bindingResult;

    @Autowired
    private Validator validator;

    @Mock
    private SessionStatus sessionStatus;

    @Mock//
    private RedirectAttributes flash;

    @Mock
    private Model model;

    SimpleDateFormat dateFormat;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(libroController).build();
    }

    @Test
    void guardar() {
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria");
        categoria.setNombreCorto("Cat");
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Categoria2");
        categoria2.setNombreCorto("Cat2");
        categorias.add(categoria);

        Libro libro = new Libro();
        libro.setAutor("Autor del libro");
        libro.setTitulo("Titulo del libro");
        libro.setIsbn("999999999");
        libro.setFechaPublicado(new Date());
        libro.setEditorial("Editorial del libro");
        libro.setCategorias(categorias);


        // Configura el comportamiento simulado del BindingResult(error=true, no error=false)
        when(bindingResult.hasErrors()).thenReturn(!validarInstancia(categoria));

        when(libroService.guardarLibro(any(Libro.class))).thenReturn(libro);

        String resultado = libroController.guardar(libro, bindingResult, sessionStatus, flash, model);

        verify(libroService, times(1)).guardarLibro(any(Libro.class));

        verify(sessionStatus, times(1)).setComplete();

        assertEquals("redirect:/libro/nuevo", resultado);
        System.out.println("Test guardar completado");
    }
    public boolean validarInstancia(Object instancia) {
        BindingResult result = new BeanPropertyBindingResult(instancia, "instancia");
        ValidationUtils.invokeValidator(validator, instancia, result);
        return !result.hasErrors(); //no errores true si hay errores false
    }
    @Test
    void editar() {
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Categoria");
        categoria.setNombreCorto("Cat");
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNombre("Categoria2");
        categoria2.setNombreCorto("Cat2");
        categorias.add(categoria);

        Libro libro = new Libro();
        libro.setId(1L);
        libro.setAutor("Autor del libro");
        libro.setTitulo("Titulo del libro");
        libro.setIsbn("999999999");
        libro.setFechaPublicado(new Date());
        libro.setEditorial("Editorial del libro");
        libro.setCategorias(categorias);


        // Configura el comportamiento simulado del BindingResult(error=true, no error=false)
        when(bindingResult.hasErrors()).thenReturn(!validarInstancia(categoria));

        when(libroService.guardarLibro(any(Libro.class))).thenReturn(libro);

        String resultado = libroController.guardar(libro, bindingResult, sessionStatus, flash, model);

        verify(libroService, times(1)).guardarLibro(any(Libro.class));

        verify(sessionStatus, times(1)).setComplete();

        assertEquals(("redirect:/libro/editar/"+libro.getId()), resultado);
        System.out.println("Test editar completado");

    }
}
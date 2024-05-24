package com.example.libreria.controladores.controller;

import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.services.daos.CategoriaService;
import com.example.libreria.services.impl.CategoriaServiceImpl;
import com.example.libreria.services.impl.MiembroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilderSupport;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration//levanta el servidor completo pero en version de test
class CategoriaControllerTest {


    MockMvc mockMvc;
    @InjectMocks
    private CategoriaController categoriaController;
    @Mock
    private CategoriaService categoriaService;

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
    void setUp() {//se ejecuta antes de cada test
        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
    }
    /*
     La idea clave aquí es verificar cómo responde tu controlador
     cuando el servicio devuelve la lista específica que has configurado
     en la prueba.
    */

    @Test
    void listar() throws Exception {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria());
        when(categoriaService.traerCategoriasSinListaDeLibros()).thenReturn(new ArrayList<>(categorias));

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/listar"))
                .andExpect(status().isOk())
                .andExpect(view().name("/categoria/lista"))
                .andExpect(model().attribute("categorias", categorias));
        verify(categoriaService, times(1)).traerCategoriasSinListaDeLibros();
        System.out.println("Test listar completado");
    }


    @Test
    void guardar() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNombre("Fantasia");
        categoria.setNombreCorto("FA");
        categoria.setNotas("No hay notas");
        categoria.setFechaCreado(new Date());


        // Configura el comportamiento simulado del BindingResult(error=true, no error=false)
        when(bindingResult.hasErrors()).thenReturn(!validarInstancia(categoria));

        when(categoriaService.guardarCategoria(any(Categoria.class))).thenReturn(categoria);

        String resultado = categoriaController.guardar(categoria, bindingResult, sessionStatus, flash, model);

        verify(categoriaService, times(1)).guardarCategoria(any(Categoria.class));

        verify(sessionStatus, times(1)).setComplete();

        assertEquals("redirect:/categoria/nuevo", resultado);
        System.out.println("Test guardar completado");
    }


    @Test
    void editar() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Fantasia");
        categoria.setNombreCorto("FAS");
        categoria.setNotas("Nueva Nota para fantasia");
        // Configura el comportamiento simulado del BindingResult
        when(bindingResult.hasErrors()).thenReturn(!validarInstancia(categoria));

        when(categoriaService.guardarCategoria(any(Categoria.class))).thenReturn(categoria);

        String resultado = categoriaController.guardar(categoria, bindingResult, sessionStatus, flash, model);

        verify(categoriaService, times(1)).guardarCategoria(any(Categoria.class));

        verify(sessionStatus, times(1)).setComplete();

        assertEquals(("redirect:/categoria/editar/"+categoria.getId()), resultado);
        System.out.println("Test editar completado");
    }

    @Test
    void eliminar() throws Exception {
        Long idCategoriaExistente = 1L;
        Categoria categoria = new Categoria();
        when(categoriaService.traerCategoriaPorId(idCategoriaExistente)).thenReturn(categoria);
        when(categoriaService.estaUsado(categoria)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/eliminar/{id}", idCategoriaExistente))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/categoria/listar"))
                .andExpect(MockMvcResultMatchers.flash().attributeCount(0));
        // Verificar que se llamaron los métodos necesarios en el dao
        verify(categoriaService, times(1)).traerCategoriaPorId(idCategoriaExistente);
        verify(categoriaService, times(1)).eliminarCategoria(idCategoriaExistente);
        System.out.println("Test eliminar completado");
    }
    @Test
    void eliminarNull() throws Exception {
        Long id = 10L;
        when(categoriaService.traerCategoriaPorId(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/categoria/eliminar/{id}", id))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/categoria/listar"))
                .andExpect(MockMvcResultMatchers.flash().attributeCount(2));
        // Verificar que se llamaron los métodos necesarios en el dao
        verify(categoriaService, times(1)).traerCategoriaPorId(id);
        verify(categoriaService, times(0)).eliminarCategoria(id);
        verify(categoriaService, times(0)).estaUsado(any(Categoria.class));
        System.out.println("Test eliminar por null completado");


    }



    public boolean validarInstancia(Object instancia) {
        BindingResult result = new BeanPropertyBindingResult(instancia, "instancia");
        ValidationUtils.invokeValidator(validator, instancia, result);
        return !result.hasErrors(); //no errores true si hay errores false
    }
}
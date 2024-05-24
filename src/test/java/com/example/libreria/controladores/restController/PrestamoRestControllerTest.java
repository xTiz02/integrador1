package com.example.libreria.controladores.restController;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroPrestado;
import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.modelo.entidades.Prestamo;
import com.example.libreria.services.daos.LibroPrestadoService;
import com.example.libreria.services.daos.LibroService;
import com.example.libreria.services.daos.MiembroService;
import com.example.libreria.services.daos.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebAppConfiguration
class PrestamoRestControllerTest {
    MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @InjectMocks
    private PrestamoRestController prestamoRestController;
    @Mock
    private PrestamoService prestamoService;
    @Mock
    private MiembroService miembroService;
    @Mock
    private LibroService libroService;
    @Mock
    private LibroPrestadoService libroPrestadoService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Autowired
    private Validator validator;
    @BeforeEach
    void setUp() {

        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoRestController).build();
    }

    @Test
    void guardar() throws Exception {
        // Simular datos de solicitud
        Map<String, String> payload = new HashMap<>();
        payload.put("miembroId", "1");
        payload.put("fechaRetorno", "2023-12-31");
        payload.put("librosId", "1,2");
        int numIds = payload.get("librosId").toString().split(",").length;
        //miembro
        Miembro miembro = new Miembro();
        miembro.setId(1L);
        miembro.setPrimerNombre("Juan");
        miembro.setApellido("Perez");
        miembro.setContacto("913249474");
        miembro.setDni("72700917");
        miembro.setEmail("prueba@gmail.com");
        miembro.setGenero(Util.MASCULINO);
        miembro.setTipo(Util.ESTUDIANTE);
        miembro.setFechaIngreso(new Date());
        miembro.setFechaNac(new Date());
        //lista de libros y libros
        List<Libro> libros = new ArrayList<>();
        Libro libro1 = new Libro(1L, "Libro 1", "tt", "uu", "ii", "81237812", 1, new Date(),null);
        Libro libro2 = new Libro(2L, "Libro 2", "yy", "uu", "ii", "91237812", 1, new Date(),null);
        libros.add(libro1);
        libros.add(libro2);
        //prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setMiembro(miembro);
        prestamo.setFechaRetorno(new Date());
        prestamo.setFechaPrestamo(new Date());
        prestamo.setEstado(Util.ESTADO_NO_DISPONIBLE);
        //libros prestado
        LibroPrestado libroPrestado1 = new LibroPrestado();
        libroPrestado1.setId(1L);
        libroPrestado1.setLibro(libro1);
        libroPrestado1.setPrestamo(prestamo);
        LibroPrestado libroPrestado2 = new LibroPrestado();
        libroPrestado2.setId(2L);
        libroPrestado2.setLibro(libro2);
        libroPrestado2.setPrestamo(prestamo);


        //test
        assertEquals(true,  validarInstancia(miembro));
        assertEquals(true,  validarInstancia(libro1));
        assertEquals(true,  validarInstancia(libro2));
        when(miembroService.traerMiembroPorId(anyLong())).thenReturn(miembro);

        when(libroService.traerLibrosPorIds(anyList())).thenReturn(libros);

        //when(prestamoService.guardarPrestamo(any(Prestamo.class))).thenReturn(prestamo);

        when(libroPrestadoService.guardarLibroPrestado(any(LibroPrestado.class))).thenReturn(libroPrestado2);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/rest/prestamo/guardar")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(construirCadena(payload)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("completado"))
                .andReturn();


        verify(miembroService, times(1)).traerMiembroPorId(anyLong());
        //verify(prestamoService, times(1)).guardarPrestamo(any(Prestamo.class));
        verify(libroPrestadoService, times(numIds)).guardarLibroPrestado(any(LibroPrestado.class));
        verify(libroService, times(numIds)).guardarLibro(any(Libro.class));

        System.out.println("Test de préstamo completado");

    }

    @Test
    void retornarTodo() throws Exception {
        // Simular un ID de préstamo existente
        Long prestamoIdExistente = 1L;

        // Realizar la solicitud GET
        mockMvc.perform(MockMvcRequestBuilders.get("/rest/prestamo/" + prestamoIdExistente))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("completado"));

        System.out.println("Test de retorno de préstamo completado");

    }

    @Test
    void returnSelected() throws Exception {
        //libros
        Libro libro1 = new Libro(1L, "Libro 1", "tt", "uu", "ii", "81237812", 2, new Date(),null);
        Libro libro2 = new Libro(2L, "Libro 2", "yy", "uu", "ii", "91237812", 2, new Date(),null);

        //prestamo
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setFechaRetorno(new Date());
        prestamo.setFechaPrestamo(new Date());
        prestamo.setEstado(Util.ESTADO_NO_DISPONIBLE);
        //libros prestado
        LibroPrestado libroPrestado1 = new LibroPrestado();
        libroPrestado1.setId(1L);
        libroPrestado1.setLibro(libro1);
        libroPrestado1.setPrestamo(prestamo);
        libroPrestado1.setDevuelto(Util.ESTADO_NO_DISPONIBLE);
        LibroPrestado libroPrestado2 = new LibroPrestado();
        libroPrestado2.setId(2L);
        libroPrestado2.setLibro(libro2);
        libroPrestado2.setDevuelto(Util.ESTADO_NO_DISPONIBLE);
        libroPrestado2.setPrestamo(prestamo);
        //Lista de libros del prestamo
        List<LibroPrestado> librosPrestados = new ArrayList<>();
        librosPrestados.add(libroPrestado1);
        librosPrestados.add(libroPrestado2);
        prestamo.setLibrosPrestados(librosPrestados);


        assertEquals(true,  validarInstancia(libro1));
        assertEquals(true,  validarInstancia(libro2));
        assertEquals(true,  validarInstancia(libroPrestado1));
        assertEquals(true,  validarInstancia(libroPrestado2));
        assertEquals(true,  validarInstancia(prestamo));



        Map<String, String> payload = new HashMap<>();
        payload.put("ids", "1,2");
        int numIds = payload.get("ids").toString().split(",").length;

        when(prestamoService.traerPrestamoPorId(anyLong())).thenReturn(prestamo);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/prestamo/{id}", 1L)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("ids", payload.get("ids"))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("completado"));

        // Verificar las llamadas a los servicios
        verify(prestamoService, Mockito.times(1)).traerPrestamoPorId(Mockito.eq(1L));
        verify(libroPrestadoService, Mockito.times(numIds)).guardarLibroPrestado(Mockito.any());
        verify(libroService, Mockito.times(numIds)).guardarLibro(Mockito.any());

        System.out.println("Test de returnSelected completado");


    // ...
    }
    private String construirCadena(Map<String, String> datos) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : datos.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return builder.toString();
    }
    public boolean validarInstancia(Object instancia) {
        BindingResult result = new BeanPropertyBindingResult(instancia, "instancia");
        ValidationUtils.invokeValidator(validator, instancia, result);
        return !result.hasErrors(); //no errores true si hay errores false
    }

}
package com.example.libreria.services.impl;

import com.example.libreria.config.CambioStock;
import com.example.libreria.config.Util;
import com.example.libreria.modelo.daos.IPrestamoDao;
import com.example.libreria.modelo.entidades.*;
import com.example.libreria.modelo.entidades.dto.PrestamoDto;
import com.example.libreria.services.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private IPrestamoDao prestamoDao;
    @Autowired
    private LibroService libroService;
    @Autowired
    private MiembroService miembroService;
    @Autowired
    private LibroUnidadService libroUnidadService;

    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public List<Prestamo> traerPrestamos() {
        return prestamoDao.findAll();
    }

    @Override
    public Prestamo traerPrestamoPorId(Long id) {
        return prestamoDao.findById(id).orElse(null);
    }

    @Override
    public Prestamo guardarPrestamo(PrestamoDto data) throws ParseException {
        Date fechaEstimadoRetornoDate = null;
        try {
            fechaEstimadoRetornoDate = formatoFecha.parse(data.getFechaRetorno());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<Long> idsUnidades = data.getLibros().keySet().stream().toList();


        Miembro miembro = miembroService.traerMiembroPorId(data.getMiembro());
        List<LibroUnidad> libros = libroUnidadService.traerLibrosUnidadPorIds(idsUnidades);


        Prestamo prestamo = new Prestamo();
        prestamo.setMiembro(miembro);
        prestamo.setFechaRetorno(fechaEstimadoRetornoDate);
        prestamo.setFechaPrestamo(new Date());
        prestamo.setEstado(Util.ESTADO_NO_DISPONIBLE);
        List<LibroPrestado> librosPrestados = new ArrayList<>();
        for (LibroUnidad libroUnidad : libros) {
            LibroPrestado libroPrestado = new LibroPrestado();
            Libro libro = libroService.cambiarStock(libroUnidad.getLibro().getId(), CambioStock.RESTAR, 1);
            libroPrestado.setLibro(libro);
            libroPrestado.setPrestamo(prestamo);
            libroPrestado.setDevuelto(Util.ESTADO_NO_DISPONIBLE);
            for(Map.Entry<Long, String> entry : data.getLibros().entrySet()) {
                if(entry.getKey().equals(libroUnidad.getId())) {
                    libroPrestado.setFechaRetorno(formatoFecha.parse(entry.getValue()));
                }
            }
            libroUnidadService.cambiarEstado(libroUnidad.getId(), Util.ESTADO_PRESTAMO);
            libroPrestado.setLibroUnidad(libroUnidad);
            librosPrestados.add(libroPrestado);
        }
        prestamo.setLibrosPrestados(librosPrestados);

        return prestamoDao.save(prestamo);
    }

    @Override
    public void eliminarPrestamo(Long id) {
        prestamoDao.deleteById(id);
    }

    @Override
    public Prestamo retornarPrestamo(Long idPrestamo, String[] unidadesIds) {
        Prestamo prestamo = prestamoDao.findById(idPrestamo).orElse(null);
        List<LibroPrestado> librosPrestados = prestamo.getLibrosPrestados();
        int estado = 0;
        if (unidadesIds != null && unidadesIds.length > 0) {
            System.out.println("unidades: " );
            for (LibroPrestado lp : librosPrestados) {
                if (ArrayUtils.contains(unidadesIds, lp.getLibroUnidad().getId().toString())) {
                    System.out.println("u");
                    lp.setDevuelto(Util.ESTADO_DISPONIBLE);
                    //libroPrestadoService.guardarLibroPrestado(lp);
                    LibroUnidad libroUnidad = lp.getLibroUnidad();
                    libroUnidad.setEstado(Util.ESTADO_DISPONIBLE);
                    libroService.cambiarStock(libroUnidad.getLibro().getId(), CambioStock.SUMAR, 1);
                    //libroService.guardarLibro(libroUnidad.getLibro());
                }
                if(lp.getDevuelto() != Util.ESTADO_DISPONIBLE){
                    estado++;
                }
            }
            if(estado == 0){
                prestamo.setEstado(Util.ESTADO_DISPONIBLE);
            }
        }else{
            return null;
        }
        return prestamoDao.save(prestamo);
    }

    @Override
    public List<Prestamo> traerPrestamosNoRetornados(Integer id) {
        return prestamoDao.findByEstado(id);
    }
}

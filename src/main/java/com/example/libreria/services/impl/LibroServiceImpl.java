package com.example.libreria.services.impl;

import com.example.libreria.config.CambioStock;
import com.example.libreria.config.Util;
import com.example.libreria.modelo.daos.ILibroDao;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.services.daos.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
@Service
public class LibroServiceImpl implements LibroService {
    @Autowired
    private ILibroDao libroDao;
    @Override
    @Transactional(readOnly = true)
    public Libro traerLibroPorTag(String tag) {
        return libroDao.findByTag(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro traerLibroPorId(Long id) {
        return libroDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> traerLibrosPorIds(List<Long> ids) {
        return libroDao.findAllById(ids);
    }

    @Override
    @Transactional
    public Libro guardarLibro(Libro libro) {
        if (libro.getId() == null) {
            libro.setFechaPublicado(new Date());
            libro.setEstado(Util.ESTADO_DISPONIBLE);
            libro.setStockDisponible(0);
            libro.setStockTotal(0);
            libro.setStockPrestado(0);
        }else{
            libro.setFechaActualizado(new Date());
        }
        return libroDao.save(libro);
    }

    @Transactional
    @Override
    public Libro cambiarStock(Long idLibro, CambioStock cambioStock, Integer cantidad) {
        Libro libro = libroDao.findById(idLibro).orElse(null);
        if(cambioStock == CambioStock.SUMAR){
            libro.setStockDisponible(libro.getStockDisponible() + cantidad);
            libro.setStockPrestado(libro.getStockPrestado() - cantidad);
        }
        if(cambioStock == CambioStock.RESTAR) {
            libro.setStockDisponible(libro.getStockDisponible() - cantidad);
            libro.setStockPrestado(libro.getStockPrestado() + cantidad);
        }
        if(libro.getStockDisponible() == 0){
            libro.setEstado(Util.ESTADO_NO_DISPONIBLE);
        }else {
            libro.setEstado(Util.ESTADO_DISPONIBLE);
        }
        return libroDao.save(libro);
    }

    @Override
    public void cambiarEstadoLibro(Long id, Integer estado) {
        Libro libro = libroDao.findById(id).orElse(null);
        if (libro != null) {
            libro.setEstado(estado);
            libroDao.save(libro);
        }
    }



    @Override
    @Transactional
    public void eliminarLibro(Long id) {
          libroDao.deleteById(id);
    }

    @Override
    public boolean estaUsado(Libro libro) {
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> traerLibros() {
        return libroDao.findAll();
    }

    @Override
    public List<Libro> traerLibrosPorCategoriaYEstado(Long id,Integer estado) {
        return libroDao.listarLibrosPorCategoriaYEstado(id,estado);
    }
}

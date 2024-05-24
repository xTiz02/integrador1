package com.example.libreria.services.impl;

import com.example.libreria.config.Util;
import com.example.libreria.modelo.daos.ILibroDao;
import com.example.libreria.modelo.daos.ILibroUnidadDao;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroUnidad;
import com.example.libreria.services.daos.LibroUnidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class LibroUnidadServiceImpl implements LibroUnidadService {

    @Autowired
    private ILibroUnidadDao libroUnidadDao;
    @Autowired
    private ILibroDao libroDao;

    @Transactional
    @Override
    public LibroUnidad guardarLibroUnidad(LibroUnidad libroUnidad) {
        if (libroUnidad.getId() == null) {
            libroDao.addStock(libroUnidad.getLibro().getId(), 1);
            libroUnidad.setEstado(Util.ESTADO_DISPONIBLE);
        }
        return libroUnidadDao.save(libroUnidad);

    }

    @Transactional
    @Override
    public void eliminarLibroUnidad(Long id) {
        libroDao.restarStock(libroUnidadDao.findById(id).get().getLibro().getId(), 1);
        libroUnidadDao.deleteById(id);
    }
    @Transactional(readOnly = true)
    @Override
    public LibroUnidad traerLibroUnidad(Long id) {
        return libroUnidadDao.findById(id).orElse(null);
    }
    @Transactional(readOnly = true)
    @Override
    public List<LibroUnidad> traerLibrosUnidadPorIsbn(String isbn) {
        return libroUnidadDao.findByIsbn(isbn);
    }

    @Override
    public List<LibroUnidad> traerLibrosUnidadPorIds(List<Long> ids) {
        return libroUnidadDao.findAllById(ids);
    }

    @Override
    @Transactional
    public void cambiarEstado(Long id, Integer estado) {
        libroUnidadDao.changeState(id, estado);
    }
}

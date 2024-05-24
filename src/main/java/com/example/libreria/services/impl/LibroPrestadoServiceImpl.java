package com.example.libreria.services.impl;

import com.example.libreria.modelo.daos.ILibroPrestadoDao;
import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroPrestado;
import com.example.libreria.services.daos.LibroPrestadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroPrestadoServiceImpl implements LibroPrestadoService {

    @Autowired
    private ILibroPrestadoDao libroPrestadoDao;

    @Override
    @Transactional(readOnly = true)
    public Long contarLibroPrestadoPorEstado(Libro libro, Integer estado) {
        return libroPrestadoDao.countByLibroAndDevuelto(libro,estado);
    }

    @Override
    @Transactional
    public LibroPrestado guardarLibroPrestado(LibroPrestado libroPrestado) {
        return libroPrestadoDao.save(libroPrestado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibroPrestado> traerLibroPrestado() {
        return libroPrestadoDao.findAll();
    }
}

package com.example.libreria.services.daos;

import com.example.libreria.modelo.entidades.LibroUnidad;

import java.util.List;

public interface LibroUnidadService {

    public LibroUnidad guardarLibroUnidad(LibroUnidad libroUnidad);
    public void eliminarLibroUnidad(Long id);
    public LibroUnidad traerLibroUnidad(Long id);
    public List<LibroUnidad> traerLibrosUnidadPorIsbn(String isbn);
    public List<LibroUnidad> traerLibrosUnidadPorIds(List<Long> ids);
    public void cambiarEstado(Long id, Integer estado);
}

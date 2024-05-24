package com.example.libreria.services.daos;

import com.example.libreria.config.CambioStock;
import com.example.libreria.modelo.entidades.Libro;

import java.util.List;

public interface LibroService {
    public Libro traerLibroPorTag(String tag);
    public Libro traerLibroPorId(Long id);
    public List<Libro> traerLibrosPorIds(List<Long> ids);
    public Libro guardarLibro(Libro libro);

    public Libro cambiarStock(Long idLibro, CambioStock cambioStock, Integer cantidad);

    public void cambiarEstadoLibro(Long id, Integer estado);
    public void eliminarLibro(Long id);
    public boolean estaUsado(Libro libro);
    public List<Libro> traerLibros();
    public List<Libro> traerLibrosPorCategoriaYEstado(Long id,Integer estado);


}

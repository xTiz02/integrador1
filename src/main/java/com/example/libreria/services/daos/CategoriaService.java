package com.example.libreria.services.daos;

import com.example.libreria.modelo.entidades.Categoria;

import java.util.List;

public interface CategoriaService {
    public List<Categoria> traerCategoriasSinListaDeLibros();
    public Categoria traerCategoriaPorId(Long id);
    public Categoria guardarCategoria(Categoria categoria);
    public void eliminarCategoria(Long id);
    public boolean estaUsado(Categoria categoria);

}

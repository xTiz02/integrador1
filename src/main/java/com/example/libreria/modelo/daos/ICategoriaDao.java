package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICategoriaDao extends JpaRepository<Categoria, Long> {
    @Query("select c from Categoria c")//trae todas las categorias sin libros
    public List<Categoria> listarCategoriasSinListaDeLibros();


}

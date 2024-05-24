package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILibroPrestadoDao extends JpaRepository<LibroPrestado, Long> {
    public Long countByLibroAndDevuelto(Libro libro, Integer devuelto);//cuanta los libros prestados por estado
}

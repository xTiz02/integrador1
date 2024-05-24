package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.LibroUnidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibroUnidadDao extends JpaRepository<LibroUnidad, Long>{
    public List<LibroUnidad> findByIsbn(String isbn);
    @Query("update LibroUnidad l set l.estado = ?2 where l.id = ?1")
    @Modifying
    public void changeState(Long id, Integer state);
}

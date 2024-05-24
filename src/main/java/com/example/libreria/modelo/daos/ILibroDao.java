package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibroDao extends JpaRepository<Libro, Long>{
    public Libro findByTag(String tag);

    //update
    @Query("update Libro l set l.stockDisponible = l.stockDisponible + ?2, l.stockTotal=l.stockTotal+?2 where l.id = ?1")
    @Modifying
    public void addStock(Long id, Integer cantidad);

    @Query("update Libro l set l.stockDisponible = l.stockDisponible - ?2, l.stockTotal=l.stockTotal-?2 where l.id = ?1")
    @Modifying
    public void restarStock(Long id, Integer cantidad);
    @Query("select l from Libro l join l.categorias c where c.id = ?1 and l.estado = ?2")
    public List<Libro> listarLibrosPorCategoriaYEstado(Long id, Integer estado);
}

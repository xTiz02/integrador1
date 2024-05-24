package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPrestamoDao extends JpaRepository<Prestamo, Long> {

    public List<Prestamo> findByEstado (Integer estado); //busca todos los prestamos que por estado
}

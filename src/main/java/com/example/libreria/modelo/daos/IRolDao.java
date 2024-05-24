package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolDao extends JpaRepository<Rol, Long> {

}

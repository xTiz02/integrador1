package com.example.libreria.modelo.daos;

import com.example.libreria.modelo.entidades.Miembro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
public interface IMiembroDao extends JpaRepository<Miembro, Long> {
    public Long countByTipo(String type);
    public List<Miembro> findByTipo(String type);
}

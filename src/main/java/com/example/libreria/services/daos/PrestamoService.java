package com.example.libreria.services.daos;

import com.example.libreria.modelo.entidades.Prestamo;
import com.example.libreria.modelo.entidades.dto.PrestamoDto;

import java.text.ParseException;
import java.util.List;

public interface PrestamoService {
    public List<Prestamo> traerPrestamos();
    public Prestamo traerPrestamoPorId(Long id);
    public Prestamo guardarPrestamo(PrestamoDto prestamo) throws ParseException;
    public void eliminarPrestamo(Long id);

    public Prestamo retornarPrestamo(Long idPrestamo, String[] unidadesIds);
    public List<Prestamo> traerPrestamosNoRetornados(Integer id);

}

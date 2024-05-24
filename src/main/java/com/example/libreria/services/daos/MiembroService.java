package com.example.libreria.services.daos;

import com.example.libreria.modelo.entidades.Miembro;

import java.util.List;

public interface MiembroService {
    public List<Miembro> traerMiembros();
    public Miembro traerMiembroPorId(Long id);
    public Miembro guardarMiembro(Miembro miembro);
    public void eliminarMiembro(Long id);
    public boolean estaUsado(Miembro miembro);
    public List<Miembro> traerMiembrosPorTipo(String tipo);

}

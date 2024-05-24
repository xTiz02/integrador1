package com.example.libreria.modelo.entidades.dto;

import com.example.libreria.modelo.entidades.Prestamo;

import java.io.Serializable;
import java.util.Map;

public class PrestamoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<Long,String> libros;
    String fechaRetorno;

    Long miembro;



    public String getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(String fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public Map<Long, String> getLibros() {
        return libros;
    }

    public void setLibros(Map<Long, String> libros) {
        this.libros = libros;
    }

    public Long getMiembro() {
        return miembro;
    }

    public void setMiembro(Long miembro) {
        this.miembro = miembro;
    }

    @Override
    public String toString() {
        return "PrestamoDto{" +
                "libros=" + libros +
                ", fechaRetorno='" + fechaRetorno + '\'' +
                ", miembro=" + miembro +
                '}';
    }
}

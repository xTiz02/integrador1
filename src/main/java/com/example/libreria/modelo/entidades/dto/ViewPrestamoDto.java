package com.example.libreria.modelo.entidades.dto;

import com.example.libreria.modelo.entidades.Prestamo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ViewPrestamoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private MiembroDto miembro;
    private List<LibroPrestadoDto> librosPrestados;
    private Date fechaPrestamo;
    private Date fechaRetorno;
    private Integer estado;

    public ViewPrestamoDto() {
    }

    public ViewPrestamoDto(MiembroDto miembro, List<LibroPrestadoDto> librosPrestados, Date fechaPrestamo, Date fechaRetorno, Integer estado) {
        this.miembro = miembro;
        this.librosPrestados = librosPrestados;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaRetorno = fechaRetorno;
        this.estado = estado;
    }
    public static ViewPrestamoDto toPrestamoDto(Prestamo prestamo){
        ViewPrestamoDto viewPrestamoDto = new ViewPrestamoDto();
        viewPrestamoDto.setEstado(prestamo.getEstado());
        viewPrestamoDto.setFechaPrestamo(prestamo.getFechaPrestamo());
        viewPrestamoDto.setFechaRetorno(prestamo.getFechaRetorno());
        viewPrestamoDto.setMiembro(MiembroDto.toMiembroDto(prestamo.getMiembro()));
        viewPrestamoDto.setLibrosPrestados(LibroPrestadoDto.toLibroPrestadoDto(prestamo.getLibrosPrestados()));
        return viewPrestamoDto;
    }
    public MiembroDto getMiembro() {
        return miembro;
    }

    public void setMiembro(MiembroDto miembro) {
        this.miembro = miembro;
    }

    public List<LibroPrestadoDto> getLibrosPrestados() {
        return librosPrestados;
    }

    public void setLibrosPrestados(List<LibroPrestadoDto> librosPrestados) {
        this.librosPrestados = librosPrestados;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}

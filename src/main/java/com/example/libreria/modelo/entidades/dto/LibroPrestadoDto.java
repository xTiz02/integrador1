package com.example.libreria.modelo.entidades.dto;

import com.example.libreria.modelo.entidades.LibroPrestado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibroPrestadoDto implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private Long UnidadId;
    private String isbn;
    private String titulo;
    private Integer estado;
    private String ubicacion;
    private Date fechaRetorno;

    public LibroPrestadoDto() {
    }
    public static List<LibroPrestadoDto> toLibroPrestadoDto(List<LibroPrestado> libroPrestados){
        List<LibroPrestadoDto> librosPrestadosDtos = new ArrayList<>();
        for (LibroPrestado libroPrestado : libroPrestados) {
            LibroPrestadoDto libroUnidadDto = new LibroPrestadoDto();
            libroUnidadDto.setTitulo(libroPrestado.getLibroUnidad().getLibro().getTitulo());
            libroUnidadDto.setUbicacion(libroPrestado.getLibroUnidad().getUbicacion());
            libroUnidadDto.setEstado(libroPrestado.getDevuelto());
            libroUnidadDto.setFechaRetorno(libroPrestado.getFechaRetorno());
            libroUnidadDto.setIsbn(libroPrestado.getLibroUnidad().getLibro().getIsbn());
            libroUnidadDto.setUnidadId(libroPrestado.getLibroUnidad().getId());
            librosPrestadosDtos.add(libroUnidadDto);
        }
        return librosPrestadosDtos;
    }
    public LibroPrestadoDto(Long unidadId, String isbn, String titulo, Integer estado, String ubicacion, Date fechaRetorno) {
        UnidadId = unidadId;
        this.isbn = isbn;
        this.titulo = titulo;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.fechaRetorno = fechaRetorno;
    }

    public Long getUnidadId() {
        return UnidadId;
    }

    public void setUnidadId(Long unidadId) {
        UnidadId = unidadId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }
}

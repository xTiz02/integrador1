package com.example.libreria.modelo.entidades;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "LibroUnidad")
public class LibroUnidad implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "estado")
    private Integer estado;

    @Column(name = "fecha_ingreso")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaIngreso;
    @Column
    private String ubicacion;
    @Column(name = "nota")
    private String nota;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id")
    @NotNull(message = "El libro es requerido")
    private Libro libro;



    public LibroUnidad() {
    }

    public LibroUnidad(String isbn, Integer estado, Date fechaIngreso, String ubicacion, String nota, Libro libro) {
        this.isbn = isbn;
        this.estado = estado;
        this.fechaIngreso = fechaIngreso;
        this.ubicacion = ubicacion;
        this.nota = nota;
        this.libro = libro;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }



    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return "{ \"id\":" + id +
                ", \"isbn\":\"" + isbn + '\"' +
                ", \"estado\":" + estado +
                ", \"fechaIngreso\":\"" + fechaIngreso + '\"' +
                ", \"ubicacion\":\"" + ubicacion + '\"' +
                ", \"nota\":\"" + nota + '\"' +
                ", \"libro\":" + libro +
                '}';
    }
}

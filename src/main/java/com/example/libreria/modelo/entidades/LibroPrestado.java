package com.example.libreria.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "libro_prestado")
public class LibroPrestado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "libro_id")
    @NotNull(message = "El libro es requerido")
    private Libro libro;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "prestamo_id")
    @NotNull
    private Prestamo prestamo;
    @Column(name = "fecha_retorno")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaRetorno;//fecha de retorno esperada
    @Column(name = "devuelto")
    private Integer devuelto;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "libro_unidad_id")
    private LibroUnidad libroUnidad;

//    @PrePersist
//    public void prePersist() {
//        this.libro.setStockPrestado(this.libro.getStockPrestado() + 1);
//        this.libro.setStockDisponible(this.libro.getStockDisponible() - 1);
//    }
//
//    @PreRemove
//    public void preRemove() {
//        this.libro.setStockPrestado(this.libro.getStockPrestado() - 1);
//        this.libro.setStockDisponible(this.libro.getStockDisponible() + 1);
//    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public LibroUnidad getLibroUnidad() {
        return libroUnidad;
    }

    public void setLibroUnidad(LibroUnidad libroUnidad) {
        this.libroUnidad = libroUnidad;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public Integer getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Integer devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "LibroPrestado{" +
                "id=" + id +
                ", libro=" + libro +
                ", prestamo=" + prestamo +
                ", fechaRetorno=" + fechaRetorno +
                ", devuelto=" + devuelto +
                '}';
    }
}

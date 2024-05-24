package com.example.libreria.modelo.entidades;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prestamo")
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    @Column(name = "fecha_prestamo")
    private Date fechaPrestamo;//fecha de prestamo

    @Column(name = "notas")
    private String notas;//notas


    @Column(name = "fecha_retorno")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaRetorno;//fecha de retorno esperada

    @Column(name = "estado")
    private Integer estado;//retornado

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)//muchos prestamos pueden ser hechos por un miembro
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;//miembro

    @JsonIgnore
    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LibroPrestado> librosPrestados;//libros prestados


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
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

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public List<LibroPrestado> getLibrosPrestados() {
        return librosPrestados;
    }

    public void setLibrosPrestados(List<LibroPrestado> librosPrestados) {
        this.librosPrestados = librosPrestados;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", fechaPrestamo=" + fechaPrestamo +
                ", notas='" + notas + '\'' +
                ", fechaRetorno=" + fechaRetorno +
                ", estado=" + estado +
                ", miembro=" + miembro+
                '}';
    }
}

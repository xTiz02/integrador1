package com.example.libreria.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "*Por favor ingrese el nombre de la categoria(N)")
    @NotBlank(message = "*Por favor ingrese el nombre de la categoria(E)")
    @Column(name = "nombre")
    @Length(max = 20, message = "*Máximo 20 caracteres.")
    private String nombre;

    @NotNull(message = "*Por favor ingrese el nombre corto de la categoria(N)")
    @NotBlank(message = "*Por favor ingrese el nombre corto de la categoria(E)")
    @Length(max = 4, message = "*Máximo 4 caracteres.")
    @Column(name = "nombre_corto")
    private String nombreCorto;//nombre corto

    @Column(name = "notas")
    @Length(max = 200, message = "*Máximo 200 caracteres.")
    private String notas;//notas

    @Column(name = "fecha_creacion")
    private Date fechaCreado;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias", cascade = CascadeType.ALL, fetch = FetchType.LAZY)//mapea a categoria de la clase categoria
    private List<Libro> libros;
    //cascade = CascadeType.All hace que si se elimina una categoria se eliminen todos los libros que tengan esa categoria
    //el @Valid en el contructor es para que valide los datos que se le pasan al constructor y no deje crear un objeto con datos invalidos
    public Categoria(Long id, String nombre,String nombreCorto, String notas, Date fechaCreado, List<Libro> libros) {
        this.id = id;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.notas = notas;
        this.fechaCreado = fechaCreado;
        this.libros = libros;
    }

    public Categoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nombreCorto='" + nombreCorto + '\'' +
                ", notas='" + notas + '\'' +
                ", fechaCreado=" + fechaCreado +
                '}';
    }
}

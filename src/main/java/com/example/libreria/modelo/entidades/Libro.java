package com.example.libreria.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "libro")
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "*Por favor ingrese el titulo del libro(N)")
    @NotBlank(message = "*Por favor ingrese el titulo del libro(B)")
    //@NotEmpty(message = "*Por favor ingrese el titulo del libro(E)")
    @Column(name = "titulo")
    private String titulo;

    @NotNull(message = "*Por favor ingrese el tag(N)")//no puede ser nulo
    @NotBlank(message = "*Por favor ingrese el tag(B)")//no puede estar en blanco
    //@NotEmpty(message = "*Por favor ingrese el tag(E)")//no puede estar vacio
    @Column(name = "tag")
    @Length(max = 10, message = "*Máximo 10 caracteres.")
    private String tag;//etiqueta

    @NotNull(message = "*Por favor ingrese el autor del libro(N)")
    @NotBlank(message = "*Por favor ingrese el autor del libro(B)")
    //@NotEmpty(message = "*Por favor ingrese el autor del libro(E)")
    @Column(name = "autor")
    private String autor;

    @Column(name = "editorial")
    @NotNull(message = "*Por favor ingrese la editorial del libro(N)")
    @NotBlank(message = "*Por favor ingrese la editorial del libro(B)")
    //@NotEmpty(message = "*Por favor ingrese la editorial del libro(E)")
    @Length(max = 40, message = "*Máximo 40 caracteres.")
    private String editorial;//editorial

    @Column(name = "isbn", unique = true)
    @NotNull(message = "*Por favor ingrese el isbn del libro(N)")
    @NotBlank(message = "*Por favor ingrese el isbn del libro(B)")
    //@NotEmpty(message = "*Por favor ingrese el isbn del libro(E)")
    @Length(max = 13, message = "*Máximo 13 caracteres.")

    private String isbn;//esto es el codigo de barras del libro
    /*
     * El ISBN es un sistema internacional de numeración de libros, aprobado como norma ISO 2108. Este número
     * identifica al libro a nivel mundial, permitiéndole una mejor comercialización y distribución.*/
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "fecha_actualizado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaActualizado;//fecha de actualizacion
    @Column(name = "fecha_publicacion")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaPublicado;//fecha de creacion
    @Column
    private Integer stockTotal;
    @Column
    private Integer stockDisponible;
    @Column
    private Integer stockPrestado;

    //@JsonIgnore//hace que no se muestre en el json de respuesta de la api
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "libro_categoria",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"libro_id", "categoria_id"}))
    private List<Categoria> categorias;

    @JsonIgnore
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    private List<LibroUnidad> unidades;//libros prestados

    public Libro(Long id, String titulo, String tag, String autor, String editorial, String isbn, Integer estado, Date fechaPublicado, List<Categoria> categorias) {
        this.id = id;
        this.titulo = titulo;
        this.tag = tag;
        this.autor = autor;
        this.editorial = editorial;
        this.isbn = isbn;
        this.estado = estado;
        this.fechaPublicado = fechaPublicado;
        this.categorias = categorias;
    }

    public Libro() {
    }

    public Date getFechaActualizado() {
        return fechaActualizado;
    }

    public void setFechaActualizado(Date fechaActualizado) {
        this.fechaActualizado = fechaActualizado;
    }

    public List<LibroUnidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<LibroUnidad> unidades) {
        this.unidades = unidades;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getFechaPublicado() {
        return fechaPublicado;
    }

    public void setFechaPublicado(Date fechaPublicado) {
        this.fechaPublicado = fechaPublicado;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Integer getStockPrestado() {
        return stockPrestado;
    }

    public void setStockPrestado(Integer stockPrestado) {
        this.stockPrestado = stockPrestado;
    }

    @Override
    public String toString() {
        return "{ \"id\":" + id +
                ", \"titulo\":\"" + titulo + '\"' +
                ", \"tag\":\"" + tag + '\"' +
                ", \"autor\":\"" + autor + '\"' +
                ", \"editorial\":\"" + editorial + '\"' +
                ", \"isbn\":\"" + isbn + '\"' +
                ", \"estado\":" + estado +
                ", \"fechaActualizado\":\"" + fechaActualizado + '\"' +
                ", \"fechaPublicado\":\"" + fechaPublicado + '\"' +
                ", \"stockTotal\":" + stockTotal +
                ", \"stockDisponible\":" + stockDisponible +
                ", \"stockPrestado\":" + stockPrestado +
                '}';
    }
}

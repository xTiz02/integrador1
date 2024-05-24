package com.example.libreria.modelo.entidades;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "miembros")
public class Miembro implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "*Por favor ingrese el dni(E)")
    @NotNull(message = "*Por favor ingrese el dni(N)")
    @Column(name = "dni", unique = true)

    //hace que el dni sea unico en la base de datos
    @Length(min = 8, max = 8, message = "*El dni debe tener 8 dígitos")
    private String dni;

    @NotEmpty(message = "*Por favor seleccione un tipo de miembro(E)")
    @NotNull(message = "*Por favor seleccione un tipo de miembro(N)")
    @Column(name = "tipo")
    private String tipo;

    @NotEmpty(message = "*Por favor ingrese el primer nombre(E)")
    @NotNull(message = "*Por favor ingrese el primer nombre(N)")
    @Column(name = "primer_nombre")
    private String primerNombre;//nombre


    @Column(name = "segundo_nombre")
    private String segundoNombre;//segundo nombre
    @NotEmpty(message = "*Por favor ingrese el apellido(E)")
    @NotNull(message = "*Por favor ingrese el apellido(N)")
    @Column(name = "apellido")
    private String apellido;//apellido

    @NotEmpty(message = "*Por favor seleccione genero(E)")
    @NotNull(message = "*Por favor seleccione genero(N)")
    @Column(name = "genero")
    private String genero;

    @NotNull(message = "*Por favor ingrese fecha de nacimiento(N)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nac")
    private Date fechaNac;//fecha de nacimiento

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;//fecha de ingreso

    @Column(name = "contacto")
    @Length(min = 9, max = 15, message = "*El contacto debe tener 9 dígitos")
    private String contacto;//contacto

    @Email(message = "*Por favor ingrese un email valido(E)")
    @Column(name = "email")
    private String email;



    public Miembro(String dni, String tipo, String primerNombre, String segundo_nombre, String apellido, String genero, Date fechaNac, Date fechaIngreso, String contacto, String email) {
        this.dni = dni;
        this.tipo = tipo;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundo_nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fechaNac = fechaNac;
        this.fechaIngreso = fechaIngreso;
        this.contacto = contacto;
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Miembro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Miembro{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", tipo='" + tipo + '\'' +
                ", primerNombre='" + primerNombre + '\'' +
                ", segundoNombre='" + segundoNombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaNac=" + fechaNac +
                ", fechaIngreso=" + fechaIngreso +
                ", contacto='" + contacto + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

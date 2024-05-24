package com.example.libreria.modelo.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "*Por favor ingrese el nombre de usuario.")
    @NotEmpty(message = "*Por favor ingrese el nombre de usuario.")
    @Column(unique = true)
    @Length(max = 30, message = "*Máximo 30 caracteres.")
    private String username;
    @NotNull(message = "*Por favor ingrese el apodo.")
    @NotEmpty(message = "*Por favor ingrese el apodo.")
    @Column(name = "apodo")
    @Length(max = 30, message = "*Máximo 30 caracteres.")
    private String apodo;//apodo de usuario

    @NotEmpty(message = "*Por favor ingrese la contraseña")
    @NotNull(message = "*Por favor ingrese la contraseña.")
    @Length(max = 60, message = "*Máximo 60 caracteres.")
    private String password;


    private Boolean enabled;


    @Column(name = "fecha_creado")
    private Date fechaCreado;

    @Column(name = "fecha_actualizado")
    private Date fechaModificado;

    @JsonIgnore
    @NotEmpty(message = "*Por favor seleccione un rol.")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "rol_id"}))
    private List<Rol> roles;

    public void setId(long id) {
        this.id = id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public Date getFechaModificado() {
        return fechaModificado;
    }

    public void setFechaModificado(Date fechaModificado) {
        this.fechaModificado = fechaModificado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}

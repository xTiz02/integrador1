package com.example.libreria.modelo.entidades.dto;

import com.example.libreria.modelo.entidades.Miembro;

import java.io.Serializable;
import java.util.Date;
public class MiembroDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dni;
    private String nombre;
    private String genero;
    private Integer edad;
    private String contacto;

    public MiembroDto() {
    }

    public MiembroDto(String dni, String nombre, String genero, Integer edad, String contacto) {
        this.dni = dni;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.contacto = contacto;
    }

    public static MiembroDto toMiembroDto(Miembro miembro){
        MiembroDto miembroDto = new MiembroDto();
        miembroDto.setNombre(miembro.getPrimerNombre()+" "+
                (miembro.getSegundoNombre()!=null?miembro.getSegundoNombre()+" ":" ") +
                miembro.getApellido());
        miembroDto.setGenero(miembro.getGenero());
        miembroDto.setEdad(new Date().getYear() - miembro.getFechaNac().getYear());
        miembroDto.setDni(miembro.getDni());
        String contacto ="ND";
        if(miembro.getContacto()!=null){
            contacto = miembro.getContacto();
        }
        if (miembro.getEmail()!=null){
            contacto = contacto + " - "+miembro.getEmail();
        }
        miembroDto.setContacto(contacto);
        return miembroDto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
}

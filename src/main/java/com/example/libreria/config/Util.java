package com.example.libreria.config;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";

    public static final String PADRE = "Padre";
    public static final String ESTUDIANTE = "Estudiante";
    public static final String OTRO = "Otro";
    public static final String MASCULINO = "Masculino";
    public static final String FEMENINO = "Femenino";
    public static final String OTROS = "Otro";
    public static final List<String> TIPOS_MIEMBROS = new ArrayList<String>() {{
        add(PADRE);
        add(ESTUDIANTE);
        add(OTRO);
    }};
    public static final List<String> TIPOS_GENEROS = new ArrayList<String>() {{
        add(MASCULINO);
        add(FEMENINO);
        add(OTROS);
    }};

    public static final Integer ESTADO_DISPONIBLE = 1;
    public static final Integer ESTADO_PRESTAMO = 2;

    public static final Integer ESTADO_INCONCLUSO = 3; //el libro prestado no llego a ser entregado
    public static final Integer ESTADO_NO_DISPONIBLE = 0;
}

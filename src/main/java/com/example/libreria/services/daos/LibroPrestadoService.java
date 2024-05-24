package com.example.libreria.services.daos;

import com.example.libreria.modelo.entidades.Libro;
import com.example.libreria.modelo.entidades.LibroPrestado;

import java.util.List;

public interface LibroPrestadoService {
    public Long contarLibroPrestadoPorEstado (Libro libro , Integer estado);
    public LibroPrestado guardarLibroPrestado(LibroPrestado libroPrestado);
    public List<LibroPrestado> traerLibroPrestado ();

}

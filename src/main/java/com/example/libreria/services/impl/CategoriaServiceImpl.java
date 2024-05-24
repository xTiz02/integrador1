package com.example.libreria.services.impl;

import com.example.libreria.modelo.daos.ICategoriaDao;
import com.example.libreria.modelo.entidades.Categoria;
import com.example.libreria.services.daos.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private ICategoriaDao categoriaDao;
    @Override
    @Transactional(readOnly = true)
    public List<Categoria> traerCategoriasSinListaDeLibros() {
        return categoriaDao.listarCategoriasSinListaDeLibros();
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria traerCategoriaPorId(Long id) {
        return categoriaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaDao.save(categoria);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        categoriaDao.deleteById(id);
    }

    @Override
    public boolean estaUsado(Categoria categoria) {
        return categoria.getLibros().size()>0;
    }
}

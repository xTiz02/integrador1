package com.example.libreria.services.impl;

import com.example.libreria.modelo.daos.IMiembroDao;
import com.example.libreria.modelo.entidades.Miembro;
import com.example.libreria.services.daos.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MiembroServiceImpl implements MiembroService {

    @Autowired
    private IMiembroDao miembroDao;
    @Override
    @Transactional(readOnly = true)
    public List<Miembro> traerMiembros() {
        return miembroDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Miembro traerMiembroPorId(Long id) {
        return miembroDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Miembro guardarMiembro(Miembro miembro) {
        return miembroDao.save(miembro);
    }

    @Override
    @Transactional
    public void eliminarMiembro(Long id) {
          miembroDao.deleteById(id);
    }

    @Override
    public boolean estaUsado(Miembro miembro) {
        return false;
    }

    @Override
    public List<Miembro> traerMiembrosPorTipo(String tipo) {
        return miembroDao.findByTipo(tipo);
    }
}

package com.example.libreria.services.impl;

import com.example.libreria.modelo.daos.IRolDao;
import com.example.libreria.modelo.entidades.Rol;
import com.example.libreria.services.daos.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private IRolDao rolDao;
    @Override
    public List<Rol> listarRoles() {
        return rolDao.findAll();
    }
}

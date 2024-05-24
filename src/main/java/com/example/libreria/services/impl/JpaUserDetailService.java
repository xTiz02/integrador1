package com.example.libreria.services.impl;

import com.example.libreria.modelo.daos.IUsuarioDao;
import com.example.libreria.modelo.entidades.Rol;
import com.example.libreria.modelo.entidades.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("jpaUserDetailService")
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null) {
            logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(Rol role: usuario.getRoles()) {
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));//aqui se le asigna el rol al usuario
        }

        if(authorities.isEmpty()) {
            logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
            throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }
    @Transactional
    public Usuario guardarUsuario(Usuario usuario){
        if (usuario.getId()>0){
            usuario.setFechaModificado(new Date());
            //Actualizar
        }else {
            String password = usuario.getPassword();
            usuario.setPassword(passwordEncoder.encode(password));
        }
        return usuarioDao.save(usuario);
    }
    @Transactional
    public Usuario buscarUsuarioPorId(Long id){
        return usuarioDao.findById(id).orElse(null);
    }
    @Transactional
    public void eliminarUsuarioPorId(Long id){
        usuarioDao.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        return usuarioDao.findAll();
    }



}

package com.brittany.sprinboot_manager_task.services;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import com.brittany.sprinboot_manager_task.repositories.UsuarioRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public JpaUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioModel userDb=usuarioRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("El usuario con el email: "+email+" no se encuentra registrado en la base de datos"));

        return new User(userDb.getEmail(),userDb.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

}

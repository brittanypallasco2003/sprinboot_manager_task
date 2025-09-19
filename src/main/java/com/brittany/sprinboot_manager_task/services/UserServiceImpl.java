package com.brittany.sprinboot_manager_task.services;

import org.springframework.transaction.annotation.Transactional;

import com.brittany.sprinboot_manager_task.repositories.UsuarioRepository;

public class UserServiceImpl implements UserServiceI{

    private final UsuarioRepository usuarioRepository;


    public UserServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

}

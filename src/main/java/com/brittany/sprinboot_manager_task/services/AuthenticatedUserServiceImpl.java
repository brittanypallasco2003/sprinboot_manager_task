package com.brittany.sprinboot_manager_task.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brittany.sprinboot_manager_task.exceptions.ResourceNotFoundException;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import com.brittany.sprinboot_manager_task.repositories.UsuarioRepository;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserServiceI {

    private final UsuarioRepository userRepository;

    public AuthenticatedUserServiceImpl(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UsuarioModel getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public boolean isCurrentUser(Long userId) {
        return getCurrentUser().getId().equals(userId);
    }

}

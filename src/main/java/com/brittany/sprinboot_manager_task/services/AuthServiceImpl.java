package com.brittany.sprinboot_manager_task.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brittany.sprinboot_manager_task.DTOs.Request.LoginDTO;
import com.brittany.sprinboot_manager_task.DTOs.Request.RegisterRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.LoginResponseDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.RegisterResponseDTO;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import com.brittany.sprinboot_manager_task.repositories.UsuarioRepository;
import com.brittany.sprinboot_manager_task.utils.JwtUtils;

@Service
public class AuthServiceImpl implements AuthServiceI {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
            PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO dto) {

        String encryptedPassword = passwordEncoder.encode(dto.password());
        UsuarioModel usuarioModel = UsuarioModel.builder()
                .nombre(dto.nombre())
                .email(dto.email())
                .password(encryptedPassword)
                .build();

        UsuarioModel userCreated = usuarioRepository.save(usuarioModel);
        return mapResponseDTO(userCreated, "Usuario Registrado");

    }

    @Transactional
    @Override
    public LoginResponseDTO loginUser(LoginDTO dto) {
        String email = dto.email();
        String password = dto.password();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);

        Authentication userAuthenticated = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(userAuthenticated);

        String token = jwtUtils.createToken(userAuthenticated);

        return new LoginResponseDTO("Hola ".concat(email), email,token);
    }

    private RegisterResponseDTO mapResponseDTO(UsuarioModel user, String message) {
        return new RegisterResponseDTO(
                message, user.getId(), user.getNombre(), user.getEmail());
    }

}

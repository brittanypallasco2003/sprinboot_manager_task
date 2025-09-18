package com.brittany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brittany.models.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

}

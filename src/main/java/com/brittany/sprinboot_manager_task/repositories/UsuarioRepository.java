package com.brittany.sprinboot_manager_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brittany.sprinboot_manager_task.models.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
   boolean existsByEmail(String email);

}

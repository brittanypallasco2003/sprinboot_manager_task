package com.brittany.sprinboot_manager_task.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brittany.sprinboot_manager_task.models.TareaModel;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;

public interface TareaRepository extends JpaRepository<TareaModel, Long>{
    List<TareaModel> findByUsuario(UsuarioModel usuario);

}

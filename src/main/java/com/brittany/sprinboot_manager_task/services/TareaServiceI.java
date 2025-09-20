package com.brittany.sprinboot_manager_task.services;

import java.util.List;
import java.util.Optional;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;

public interface TareaServiceI {

    List<TareaResponseDTO> getAllTasksByUser();

    Optional<TareaResponseDTO> getTaskById(Long id);

    TareaResponseDTO createTask(TareaRequestDTO dto);

    Optional<TareaResponseDTO> updateTask(TareaRequestDTO dto);

    void deleteTask(Long id);

}

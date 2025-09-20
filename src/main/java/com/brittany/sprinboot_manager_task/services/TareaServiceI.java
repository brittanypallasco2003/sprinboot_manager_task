package com.brittany.sprinboot_manager_task.services;

import java.util.List;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;

public interface TareaServiceI {

    List<TareaResponseDTO> getAllTasksByUser();

    TareaResponseDTO getTaskById(Long id);

    TareaResponseDTO createTask(TareaRequestDTO dto);

    TareaResponseDTO updateTask(Long id, TareaRequestDTO dto);

    void deleteTask(Long id);

}

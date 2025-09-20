package com.brittany.sprinboot_manager_task.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;
import com.brittany.sprinboot_manager_task.exceptions.ResourceNotFoundException;
import com.brittany.sprinboot_manager_task.models.TareaModel;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import com.brittany.sprinboot_manager_task.repositories.TareaRepository;

@Service
public class TareaServiceImpl implements TareaServiceI {

    private final TareaRepository tareaRepository;
    private final AuthenticatedUserServiceI authenticatedUserService;

    public TareaServiceImpl(TareaRepository tareaRepository, AuthenticatedUserServiceI authenticatedUserService) {
        this.tareaRepository = tareaRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TareaResponseDTO> getAllTasksByUser() {
        UsuarioModel userDb = authenticatedUserService.getCurrentUser();

        List<TareaModel> tareas = tareaRepository.findByUsuario(userDb);
        return tareas.stream().map(
                t -> mapResponseDTO(t)).collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    @Override
    public TareaResponseDTO getTaskById(Long id) {
        TareaModel tareaDb = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        if (!authenticatedUserService.isCurrentUser(tareaDb.getUsuario().getId())) {
            throw new AccessDeniedException("No tienes permitido acceder a esta tarea");
        }
        return mapResponseDTO(tareaDb);

    }

    @Transactional
    @Override
    public TareaResponseDTO createTask(TareaRequestDTO dto) {
        UsuarioModel userDb = authenticatedUserService.getCurrentUser();

        TareaModel tareaModel = TareaModel.builder()
                .titulo(dto.titulo())
                .description(dto.descripcion())
                .estado(dto.estado())
                .usuario(userDb)
                .build();

        TareaModel tareaCreated = tareaRepository.save(tareaModel);

        return mapResponseDTO(tareaCreated);

    }

    @Transactional
    @Override
    public TareaResponseDTO updateTask(Long id, TareaRequestDTO dto) {
        TareaModel tareaDb = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));
        if (!authenticatedUserService.isCurrentUser(tareaDb.getUsuario().getId())) {
            throw new AccessDeniedException("No tienes permitido editar esta tarea");
        }
        tareaDb.setTitulo(dto.titulo());
        tareaDb.setDescription(dto.descripcion());
        tareaDb.setEstado(dto.estado());

        TareaModel tareaUpdated = tareaRepository.saveAndFlush(tareaDb);

        return mapResponseDTO(tareaUpdated);

    }

    @Transactional
    @Override
    public void deleteTask(Long id) {
        TareaModel tareaDb = tareaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));
        if (!authenticatedUserService.isCurrentUser(tareaDb.getUsuario().getId())) {
            throw new AccessDeniedException("No tienes permitido eliminar esta tarea");
        }
        tareaRepository.delete(tareaDb);
    }

    private TareaResponseDTO mapResponseDTO(TareaModel tarea) {
        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescription(),
                tarea.getEstado(),
                tarea.getCreatedAt(),
                tarea.getUpdatedAt(),
                tarea.getUsuario().getId());
    }

}

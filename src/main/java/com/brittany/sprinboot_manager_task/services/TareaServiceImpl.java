package com.brittany.sprinboot_manager_task.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;
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
    public Optional<TareaResponseDTO> getTaskById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
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

    @Override
    public Optional<TareaResponseDTO> updateTask(TareaRequestDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTask'");
    }

    @Override
    public void deleteTask(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
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

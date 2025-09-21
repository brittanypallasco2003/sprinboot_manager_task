package com.brittany.sprinboot_manager_task.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;
import com.brittany.sprinboot_manager_task.exceptions.ResourceNotFoundException;
import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;
import com.brittany.sprinboot_manager_task.models.TareaModel;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import com.brittany.sprinboot_manager_task.repositories.TareaRepository;

@ExtendWith({ MockitoExtension.class })
public class TareaServiceImplTest {

    @Mock
    private TareaRepository tareaRepository;
    @Mock
    private AuthenticatedUserServiceI authenticatedUserService;

    @InjectMocks
    private TareaServiceImpl tareaServiceImpl;

    private UsuarioModel usuario;

    @BeforeEach
    void setUp() {
        usuario = buildUsuario(1L, "Juan", "juan@example.com");
    }

    private UsuarioModel buildUsuario(Long id, String nombre, String email) {
        UsuarioModel u = new UsuarioModel();
        u.setId(id);
        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword("password123");
        return u;
    }

    private TareaModel buildTarea(Long id, String titulo, String descripcion, EstadoTareaEnum estado,
            UsuarioModel user) {
        return TareaModel.builder()
                .id(id)
                .titulo(titulo)
                .description(descripcion)
                .estado(estado)
                .usuario(user)
                .build();
    }

    @Test
    void testGetAllTasksByUser_debeRetornarTareasDelUsuario() {
        TareaModel tarea1 = buildTarea(1L, "Comprar leche", "Ir al supermercado", EstadoTareaEnum.PENDIENTE, usuario);
        TareaModel tarea2 = buildTarea(2L, "Estudiar Java", "Practicar Spring Boot", EstadoTareaEnum.EN_PROGRESO,
                usuario);

        when(authenticatedUserService.getCurrentUser()).thenReturn(usuario);
        when(tareaRepository.findByUsuario(usuario)).thenReturn(List.of(tarea1, tarea2));

        List<TareaResponseDTO> result = tareaServiceImpl.getAllTasksByUser();

        assertThat(result).hasSize(2);
        assertThat(result)
                .anyMatch(t -> t.titulo().equals("Comprar leche") && t.estado() == EstadoTareaEnum.PENDIENTE
                        && t.userId().equals(1L))
                .anyMatch(t -> t.titulo().equals("Estudiar Java") && t.estado() == EstadoTareaEnum.EN_PROGRESO
                        && t.userId().equals(1L));
        verify(tareaRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    void getTaskById_deberiaRetornarTareaMedianteId() {
        TareaModel tarea = buildTarea(3L, "Ir al gym", "Entrenar piernas", EstadoTareaEnum.EN_PROGRESO, usuario);

        when(tareaRepository.findById(3L)).thenReturn(Optional.of(tarea));
        when(authenticatedUserService.isCurrentUser(usuario.getId())).thenReturn(true);

        TareaResponseDTO result = tareaServiceImpl.getTaskById(3L);

        assertThat(result.id()).isEqualTo(3L);
        assertThat(result.titulo()).isEqualTo("Ir al gym");
        assertThat(result.description()).isEqualTo("Entrenar piernas");
        assertThat(result.estado()).isEqualTo(EstadoTareaEnum.EN_PROGRESO);
        assertThat(result.userId()).isEqualTo(usuario.getId());
        verify(tareaRepository, times(1)).findById(3L);
    }

    @Test
    void getTaskById_deberiaLanzarExcepcionCuandoNoExisteLaTarea() {
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tareaServiceImpl.getTaskById(99L));
        verify(tareaRepository, times(1)).findById(99L);
    }

    @Test
    void getTaskById_deberiaLanzarExcepcionCuandoUsuarioNoEsPropietario() {
        UsuarioModel otroUsuario = buildUsuario(2L, "Maria", "maria@example.com");
        TareaModel tarea = buildTarea(10L, "Tarea privada", "Solo dueño puede verla", EstadoTareaEnum.PENDIENTE, otroUsuario);
        when(tareaRepository.findById(10L)).thenReturn(Optional.of(tarea));
        when(authenticatedUserService.isCurrentUser(tarea.getUsuario().getId())).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> tareaServiceImpl.getTaskById(10L));
        verify(tareaRepository, times(1)).findById(10L);
    }

    @Test
    void testCreateTask_deberiaCrearTareaConUsuario() {
        TareaRequestDTO dto = new TareaRequestDTO("Comprar leche", "Ir al super", EstadoTareaEnum.PENDIENTE);

        when(authenticatedUserService.getCurrentUser()).thenReturn(usuario);
        when(tareaRepository.save(any(TareaModel.class))).thenAnswer(i -> i.getArgument(0));

        TareaResponseDTO result = tareaServiceImpl.createTask(dto);

        assertThat(result.titulo()).isEqualTo("Comprar leche");
        assertThat(result.description()).isEqualTo("Ir al super");
        assertThat(result.estado()).isEqualTo(EstadoTareaEnum.PENDIENTE);
        assertThat(result.userId()).isEqualTo(1L);

        verify(tareaRepository, times(1)).save(any(TareaModel.class));

    }

}

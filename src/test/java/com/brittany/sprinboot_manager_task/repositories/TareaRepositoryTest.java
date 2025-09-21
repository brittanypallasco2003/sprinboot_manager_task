package com.brittany.sprinboot_manager_task.repositories;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.brittany.sprinboot_manager_task.models.EstadoTareaEnum;
import com.brittany.sprinboot_manager_task.models.TareaModel;
import com.brittany.sprinboot_manager_task.models.UsuarioModel;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioModel usuario;

    @BeforeEach
    public void init() {
        usuario = new UsuarioModel();
        usuario.setNombre("juan");
        usuario.setEmail("juan@correo.com");
        usuario.setPassword("contraseña123");
        usuario = usuarioRepository.save(usuario);

        usuario = usuarioRepository.save(usuario);

        TareaModel tarea1 = TareaModel
                .builder()
                .titulo("Comprar leche")
                .description("Ir al supermercado")
                .estado(EstadoTareaEnum.PENDIENTE)
                .usuario(usuario)
                .build();

        TareaModel tarea2 = TareaModel
                .builder()
                .titulo("Estudiar Java")
                .description("Practicar Spring Boot")
                .estado(EstadoTareaEnum.EN_PROGRESO)
                .usuario(usuario)
                .build();

        tareaRepository.save(tarea1);
        tareaRepository.save(tarea2);

    }

    @Test
    void testFindByUsuario_devuelveCantidadCorrecta() {
        List<TareaModel> tareas = tareaRepository.findByUsuario(usuario);
        assertThat(tareas).hasSize(2);

    }

    @Test
    void testFindByUsuario_devuelveCamposCorrectos() {
        List<TareaModel> tareas = tareaRepository.findByUsuario(usuario);
        assertThat(tareas).anyMatch(t -> t.getTitulo().equals("Comprar leche") &&
                t.getDescription().equals("Ir al supermercado") &&
                t.getEstado() == EstadoTareaEnum.PENDIENTE);

        assertThat(tareas).anyMatch(t -> t.getTitulo().equals("Estudiar Java") &&
                t.getDescription().equals("Practicar Spring Boot") &&
                t.getEstado() == EstadoTareaEnum.EN_PROGRESO);
    }

}

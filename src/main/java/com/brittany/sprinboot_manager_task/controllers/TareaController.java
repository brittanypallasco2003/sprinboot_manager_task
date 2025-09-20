package com.brittany.sprinboot_manager_task.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.brittany.sprinboot_manager_task.DTOs.Request.TareaRequestDTO;
import com.brittany.sprinboot_manager_task.DTOs.Response.TareaResponseDTO;
import com.brittany.sprinboot_manager_task.services.TareaServiceI;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaServiceI tareaService;

    public TareaController(TareaServiceI tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        return new ResponseEntity<>(tareaService.getAllTasksByUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(tareaService.getTaskById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postTask(@RequestBody @Valid TareaRequestDTO dto) {
        TareaResponseDTO response = tareaService.createTask(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("tareas/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTask(@PathVariable Long id, @RequestBody TareaRequestDTO dto) {
        return new ResponseEntity<>(tareaService.updateTask(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        tareaService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}

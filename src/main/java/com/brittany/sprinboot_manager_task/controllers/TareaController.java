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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

}

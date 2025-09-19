package com.brittany.sprinboot_manager_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brittany.sprinboot_manager_task.models.TareaModel;

public interface TareaRepository extends JpaRepository<TareaModel, Long>{
    

}

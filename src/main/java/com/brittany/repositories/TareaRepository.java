package com.brittany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brittany.models.TareaModel;

public interface TareaRepository extends JpaRepository<TareaModel, Long>{
    

}

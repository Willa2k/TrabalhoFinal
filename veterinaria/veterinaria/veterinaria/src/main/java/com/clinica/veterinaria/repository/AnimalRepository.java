package com.clinica.veterinaria.repository;


import com.clinica.veterinaria.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}


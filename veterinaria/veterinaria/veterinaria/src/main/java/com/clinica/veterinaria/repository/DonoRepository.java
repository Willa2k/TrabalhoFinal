package com.clinica.veterinaria.repository;


import com.clinica.veterinaria.entity.Dono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonoRepository extends JpaRepository<Dono, Long> {
}

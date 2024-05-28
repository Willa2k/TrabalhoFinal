package com.clinica.veterinaria.service;

import com.clinica.veterinaria.entity.Dono;
import com.clinica.veterinaria.repository.DonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonoService {

    @Autowired
    private DonoRepository donoRepository;

    public List<Dono> findAll() {
        return donoRepository.findAll();
    }

    public Dono findById(Long id) {
        return donoRepository.findById(id).orElse(null);
    }

    public Dono save(Dono dono) {
        return donoRepository.save(dono);
    }

    public void deleteById(Long id) {
        donoRepository.deleteById(id);
    }
}


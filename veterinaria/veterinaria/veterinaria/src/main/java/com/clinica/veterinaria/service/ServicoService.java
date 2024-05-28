package com.clinica.veterinaria.service;

import com.clinica.veterinaria.entity.Dono;
import com.clinica.veterinaria.entity.Servico;
import com.clinica.veterinaria.repository.DonoRepository;
import com.clinica.veterinaria.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoService;

    public List<Servico> findAll() {
        return servicoService.findAll();
    }

    public Servico findById(Long id) {
        return servicoService.findById(id).orElse(null);
    }

    public Servico save(Servico servico) {
        return servicoService.save(servico);
    }

    public void deleteById(Long id) {
        servicoService.deleteById(id);
    }
}


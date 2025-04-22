package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Droga;
import com.example.demo.repository.DrogaRepository;

@Service
public class DrogaServiceImpl implements DrogaService {

    @Autowired
    DrogaRepository repo;

    @Override
    public List<Droga> SearchAll() {
        
        return repo.findAll(); // Cambia esto por la lógica real de búsqueda
    }
    
}

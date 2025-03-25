package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Veterinario;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    VeterinarioRepository veterinarioRepository;


    @Override
    public Veterinario searchById(Long id) {
        return veterinarioRepository.findById(id).orElse(null);
    }

    @Override
    public Veterinario searchByCedula(String cedula) {
        return veterinarioRepository.findByCedula(cedula);
    }

    @Override
    public List<Veterinario> searchAll() {
        return veterinarioRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        veterinarioRepository.deleteById(id);
    }

    @Override
    public void update(Veterinario veterinario) {
        veterinarioRepository.save(veterinario);
    }

    @Override
    public void add(Veterinario veterinario) {
        veterinarioRepository.save(veterinario);
    }

    @Override
    public Veterinario searchByNombre(String nombre) {
        return veterinarioRepository.findByNombre(nombre);
    }
}

package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Administrador;
import com.example.demo.model.Veterinario;
import com.example.demo.repository.AdministradorRepository;
import com.example.demo.repository.VeterinarioRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    AdministradorRepository administradorRepository;
    
    @Autowired
    VeterinarioRepository veterinarioRepository;

    @Override
    public Administrador searchById(Long id) {
        return administradorRepository.findById(id).orElse(null);
    }

    @Override
    public Administrador searchByCedula(String cedula) {
        return administradorRepository.findByCedula(cedula);
    }

    @Override
    public List<Administrador> searchAll() {
        return administradorRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public void update(Administrador administrador) {
        administradorRepository.save(administrador);
    }

    @Override
    public void add(Administrador administrador) {
        administradorRepository.save(administrador);
    }

    @Override
    public Administrador searchByNombre(String nombre) {
        return administradorRepository.findByNombre(nombre);
    }
    
    @Override
    public List<Veterinario> findVeterinariosByAdministradorId(Long administradorId) {
        return veterinarioRepository.findByAdministradorId(administradorId);
    }
}

package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Administrador;
import com.example.demo.model.Veterinario;

public interface AdministradorService {
    public Administrador searchById(Long id);
    public Administrador searchByCedula(String cedula);
    public List<Administrador> searchAll();
    public void deleteById(Long id);
    public void update(Administrador administrador);
    public void add(Administrador administrador);
    public Administrador searchByNombre(String nombre);
    public List<Veterinario> findVeterinariosByAdministradorId(Long administradorId);
}

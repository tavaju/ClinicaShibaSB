package com.example.demo.service;

import java.util.List;
import com.example.demo.model.Veterinario;
import com.example.demo.model.Mascota;

public interface VeterinarioService {
    public Veterinario searchById(Long id);
    public Veterinario searchByCedula(String cedula);
    public List<Veterinario> searchAll();
    public void deleteById(Long id);
    public void update(Veterinario veterinario);
    public void add(Veterinario veterinario);
    public Veterinario searchByNombre(String nombre);
    public List<Mascota> findMascotasByVeterinarioId(Long veterinarioId);
}

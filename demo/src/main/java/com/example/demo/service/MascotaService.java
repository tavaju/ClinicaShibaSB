package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import com.example.demo.model.Mascota;

public interface MascotaService {

    public Mascota SearchById(Long id);

    public Collection<Mascota> SearchAll();

    public void deleteById(Long id);

    public void update(Mascota mascota);

    public void add(Mascota mascota);

    public Collection<Mascota> findByClienteId(Long id);

    // Método para buscar mascotas por cualquier atributo
    public List<Mascota> searchByQuery(String query);
    
}
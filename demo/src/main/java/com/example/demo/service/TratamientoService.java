package com.example.demo.service;
import java.util.List;

import com.example.demo.model.Mascota;
import com.example.demo.model.Tratamiento;

public interface TratamientoService {


    public Mascota SearchById(Long id);

    public List<Mascota> SearchAll();

    public void deleteById(Long id);

    public void update(Mascota mascota);

    public void add(Mascota mascota);

    public List<Mascota> findByClienteId(Long id);
    
    // Método para buscar mascotas por cualquier atributo
    public List<Mascota> searchByQuery(String query);

      
}

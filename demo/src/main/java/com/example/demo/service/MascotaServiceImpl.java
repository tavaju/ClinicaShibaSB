package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Mascota;
import com.example.demo.repository.MascotaRepository;

@Service
public class MascotaServiceImpl  implements MascotaService {


    @Autowired 
    MascotaRepository repo;

    @Override
    public Mascota SearchById(Long id) {
        return repo.findById(id).orElse(null);
}

    @Override
    public List<Mascota> SearchAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
        }

    @Override
    public void update(Mascota mascota) {
        // Verificar que la mascota existe
        if (repo.existsById(mascota.getId())) {
            // Guardar directamente la mascota actualizada
            repo.save(mascota);
        }
    }

    @Override
    public void add(Mascota mascota) {
        repo.save(mascota);
        }

    @Override
    public Collection<Mascota> findByClienteId(Long id) {
        return repo.findByClienteId(id);
    }

    @Override
    public List<Mascota> searchByQuery(String query) {
        return repo.searchByQuery(query);
    }


    
}

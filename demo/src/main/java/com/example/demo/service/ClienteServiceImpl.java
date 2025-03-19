package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.MascotaRepository;

@Service
@Transactional // Añadir esta anotación a nivel de clase
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository repo;

    @Autowired
    MascotaRepository mascotaRepository;

    @Override
    public Cliente searchByCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public List<Cliente> searchAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void update(Cliente cliente) {
        repo.save(cliente);
    }

    @Override
    public void add(Cliente cliente) {
        repo.save(cliente);
    }

    @Override
    public Cliente searchByEmail(String email) {
        return repo.findByCorreo(email);
    }

    @Override
    public Cliente searchById(Long id) {
        return repo.findById(id).orElse(null); // Buscar cliente por ID
    }
}
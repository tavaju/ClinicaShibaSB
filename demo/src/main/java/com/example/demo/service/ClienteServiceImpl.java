package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
@Transactional  // Añadir esta anotación a nivel de clase
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository repo;

    @Override
    public Cliente searchByCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public List<Cliente> searchAll() {
        return repo.findAll();
    }

    @Override
    @Transactional  // Añadir esta anotación específicamente para el método de eliminar
    public void deleteByCedula(String cedula) {
        repo.deleteByCedula(cedula);
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
}
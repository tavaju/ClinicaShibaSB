package com.example.demo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Cliente;
import com.example.demo.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    ClienteRepository repo;

    @Override
    public Cliente searchByCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public Collection<Cliente> searchAll() {
        return repo.findAll();
    }

    @Override
    public void deleteByCedula(String cedula) {
        repo.deleteByCedula(cedula);
    }

    @Override
    public void update(Cliente cliente) {
        repo.update(cliente);
    }

    @Override
    public void add(Cliente cliente) {
        repo.add(cliente);
    }

    @Override
    public Cliente searchByEmail(String email) {
        return repo.findByEmail(email);
    }
} 
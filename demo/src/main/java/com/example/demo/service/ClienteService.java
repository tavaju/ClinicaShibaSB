package com.example.demo.service;
import java.util.List;

import com.example.demo.model.Cliente;

public interface ClienteService {
    public Cliente searchById(Long id);

    public Cliente searchByCedula(String cedula);

    public List<Cliente> searchAll();

    public void deleteById(Long id);

    public void update(Cliente cliente);

    public void add(Cliente cliente);

    public Cliente searchByEmail(String email);
}
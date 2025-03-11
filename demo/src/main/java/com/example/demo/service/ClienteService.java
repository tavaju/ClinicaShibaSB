package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import com.example.demo.entity.Cliente;

public interface ClienteService {

    public Cliente searchByCedula(String cedula);

    public List<Cliente> searchAll();

    public void deleteByCedula(String cedula);

    public void update(Cliente cliente);

    public void add(Cliente cliente);

    public Cliente searchByEmail(String email);
}
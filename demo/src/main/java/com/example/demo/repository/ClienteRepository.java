package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cliente;

// Repositorio de Cliente 
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Métodos personalizados para buscar un cliente por cédula y correo
    Cliente findByCedula(String cedula);

    Cliente findByCorreo(String correo);
}
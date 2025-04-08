package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cliente;
import com.example.demo.model.Mascota;


// Repositorio de Cliente 
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Métodos personalizados para buscar un cliente por cédula y correo
    Cliente findByCedula(String cedula);

    Cliente findByCorreo(String correo);

    Cliente findByMascotas_Id(Long mascotaId);

}
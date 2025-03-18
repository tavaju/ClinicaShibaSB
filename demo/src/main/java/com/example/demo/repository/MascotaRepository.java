package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Mascota;

import java.util.List;

// Repositorio de Mascota
@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Método personalizado para buscar una mascota por id del cliente 
    List<Mascota> findByClienteId(Long clienteId);
}






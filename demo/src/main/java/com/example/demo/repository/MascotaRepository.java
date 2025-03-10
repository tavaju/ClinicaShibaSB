package com.example.demo.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    Collection<Mascota> findByClienteCedula(String cedula); // Cambiado de findByCedulaCliente
}





